let user = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    roles: []
}
let newUser = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    roles: []
}
let listRoles = {}

function getAllPeople() {
    fetch("http://localhost:8080/api/people")
        .then(res => res.json())
        .then(people => {
            let temp = ""
            people.forEach(person => {
                temp += `<tr id=tr${person.id}>`
                temp += "<td>" + person.id + "</td>"
                temp += "<td>" + person.firstName + "</td>"
                temp += "<td>" + person.lastName + "</td>"
                temp += "<td>" + person.email + "</td>"
                temp += "<td>"
                for (const authority of person.authorities) {
                    temp += authority.role.substring(5) + " "
                }
                temp += "</td>"
                temp +=
                    `<td>
                        <button type="button" class="btn btn-info" data-bs-toggle="modal"
                                data-bs-target="#modalEdit" id="editButton${person.id}">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                data-bs-target="#modalDelete" id="deleteButton${person.id}">Delete
                        </button>
                    </td>`
                temp += "</tr>"
            })
            document.getElementById("table1").innerHTML = temp
        })
}

async function getAllRoles() {
    fetch("http://localhost:8080/api/people/roles").then(res => res.json()).then(roles => {
        console.log(roles)
        let temp = ""
        for (const r of roles) {
            temp += `<option value="${r.id}">${r.role.substring(5)}</option>`
        }
        document.getElementById("modalRoleEdit").innerHTML = temp
        document.getElementById("modalRoleDelete").innerHTML = temp
        document.getElementById("addRoles").innerHTML = temp
        listRoles = roles
    })
}

async function getModalEdit() {
    document.getElementById("table1").addEventListener('click', (e) => {
        let target = e.target.id
        if (target.includes("editButton")) {
            fetch("http://localhost:8080/api/people/" + target.substring(10))
                .then(res => res.json())
                .then(person => {
                    document.getElementById("modalIdEdit").value = person.id
                    document.getElementById("modalFirstNameEdit").value = person.firstName
                    document.getElementById("modalLastNameEdit").value = person.lastName
                    document.getElementById("modalEmailEdit").value = person.email
                    document.getElementById("modalPasswordEdit").value = person.password
                })
        }
    })
}

async function sendEditUser() {
    document.getElementById("editSubmit").addEventListener('click', () => {
        user.id = document.getElementById("modalIdEdit").value
        user.firstName = document.getElementById("modalFirstNameEdit").value
        user.lastName = document.getElementById("modalLastNameEdit").value
        user.email = document.getElementById("modalEmailEdit").value
        user.password = document.getElementById("modalPasswordEdit").value
        for (const option of document.getElementById("modalRoleEdit").options) {
            if (option.selected) {
                if (option.value == 1) {
                    user.roles.push(listRoles[0])
                } else if (option.value == 2) {
                    user.roles.push(listRoles[1])
                }
            }
        }
        fetch("http://localhost:8080/api/people", {
            method: "PUT",
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => res.json()).then(person => {
            let temp = ""
            temp += `<tr id=tr${person.id}>`
            temp += "<td>" + person.id + "</td>"
            temp += "<td>" + person.firstName + "</td>"
            temp += "<td>" + person.lastName + "</td>"
            temp += "<td>" + person.email + "</td>"
            temp += "<td>"
            for (const authority of person.authorities) {
                temp += authority.role.substring(5) + " "
            }
            temp += "</td>"
            temp +=
                `<td>
                        <button type="button" class="btn btn-info" data-bs-toggle="modal"
                                data-bs-target="#modalEdit" id="editButton${person.id}">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                data-bs-target="#modalDelete" id="deleteButton${person.id}">Delete
                        </button>
                    </td>`
            temp += "</tr>"
            document.getElementById("tr" + user.id).innerHTML = temp
            document.getElementById("editClose").click()
            user.roles = []
        })
    })
}

async function getModalDelete() {
    document.getElementById("table1").addEventListener('click', (e) => {
        let target = e.target.id
        if (target.includes("deleteButton")) {
            fetch("http://localhost:8080/api/people/" + target.substring(12))
                .then(res => res.json())
                .then(person => {
                    document.getElementById("modalIdDelete").value = person.id
                    document.getElementById("modalFirstNameDelete").value = person.firstName
                    document.getElementById("modalLastNameDelete").value = person.lastName
                    document.getElementById("modalEmailDelete").value = person.email
                })
        }
    })
}

async function deleteUser() {
    document.getElementById("deleteSubmit").addEventListener("click", () => {
            fetch("http://localhost:8080/api/people/" + document.getElementById("modalIdDelete").value, {
                method: "DELETE"
            })
            document.getElementById("deleteClose").click()
            document.getElementById("tr" + document.getElementById("modalIdDelete").value).remove()
        }
    )
}

async function addNewUser() {
    document.getElementById("addSubmit").addEventListener("click", () => {
        newUser.firstName = document.getElementById("addFirstName").value
        newUser.lastName = document.getElementById("addLastName").value
        newUser.email = document.getElementById("addEmail").value
        newUser.password = document.getElementById("addPassword").value
        for (const option of document.getElementById("addRoles").options) {
            if (option.selected) {
                if (option.value == 1) {
                    newUser.roles.push(listRoles[0])
                } else if (option.value == 2) {
                    newUser.roles.push(listRoles[1])
                }
            }
        }
        fetch("http://localhost:8080/api/people", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        }).then(res => res.json()).then(person => {
            let temp = ""
            temp += `<tr id=tr${person.id}>`
            temp += "<td>" + person.id + "</td>"
            temp += "<td>" + person.firstName + "</td>"
            temp += "<td>" + person.lastName + "</td>"
            temp += "<td>" + person.email + "</td>"
            temp += "<td>"
            for (const authority of person.authorities) {
                temp += authority.role.substring(5) + " "
            }
            temp += "</td>"
            temp +=
                `<td>
                        <button type="button" class="btn btn-info" data-bs-toggle="modal"
                                data-bs-target="#modalEdit" id="editButton${person.id}">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                data-bs-target="#modalDelete" id="deleteButton${person.id}">Delete
                        </button>
                    </td>`
            temp += "</tr>"
            document.getElementById("table1").insertAdjacentHTML("beforeend", temp)
            document.getElementById("addFirstName").value = ''
            document.getElementById("addLastName").value = ''
            document.getElementById("addEmail").value = ''
            document.getElementById("addPassword").value = ''
            document.getElementById("addRoles").selectedIndex = -1
            document.getElementById("navUsersTableTab").click()
        })
        newUser.roles = []
    })
}

getAllPeople()
getAllRoles()
getModalEdit()
sendEditUser()
getModalDelete()
deleteUser()
addNewUser()
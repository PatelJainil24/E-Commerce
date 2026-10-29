import { useEffect, useState } from "react";
import Header from "../common/Header";
import axios from "axios";


const UserList = () =>{


    const [users,setUsers] = useState([]);

    useEffect(() => {
        fetchUsers();
    },[]);

    const fetchUsers = async() => {

        try {
            
            const response= await axios.get("http://localhost:8050/api/users");
            setUsers(response.data);
            console.log(response.data);
            

        } catch (err) {
            console.log("Flaied to fetch Users:",err);
            
        }
    }

   

    return (
        <div className="container-fluid">
            <Header />

            {users.length>0 ? (
                <div className="mt-4">
                <h2 className="text-success mb-4">User Details</h2>
                <div className="row justify-content-center">
                    <div className="col-10">
                        <table className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Role</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    users.map((user, index) => (
                                        <tr>
                                            <td>{user.firstName}</td>
                                            <td>{user.lastName}</td>
                                            <td>{user.credential.username}</td>
                                            <td>{user.email}</td>
                                            <td>{user.phone}</td>
                                            <td>{user.credential.roleBasedAuthority}</td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            ) : (
                <div>
                    <h3>User Not Found... Please try again later </h3>
                </div>
            )}


            


        </div>
    )
}

export default UserList;
import axios from "axios";
import Header from "../common/Header";
import { useState } from "react";
import { ToastContainer, toast } from 'react-toastify';


const ChangePassword = () => {

    const storedUser = localStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null;
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');

    const updatePassword = async (e) => {
        e.preventDefault()
        const request = {
            username: user.credential.username,
            oldPassword,
            newPassword
        }

        try {
            const res = await axios.patch("http://localhost:8050/api/credentials/change-password", request);
            if (res.status === 200) {
                toast.success("Password Changed Successfully");
            }

        } catch (err) {
            console.log("Faid to Change Password", err);
            toast.error(err.message || "Failed to change password");

        }
    }

    return (
        <div className="container-fluid">
            <Header />
            <div className="mt-4">
                <h2 className="text-center">Change Password</h2>
                <div className="row justify-content-center">
                    <div className="col-4">
                        <form onSubmit={updatePassword} className="card p-4 shadow">
                            <div className="form-floating mb-3">
                                <input type="password" className="form-control" placeholder="Old Password" onChange={(e) => setOldPassword(e.target.value)} required />
                                <label>Old Password</label>
                            </div>
                            <div className="form-floating mb-3">
                                <input type="password" className="form-control" placeholder="New Password" onChange={(e) => setNewPassword(e.target.value)} required />
                                <label>New Password</label>
                            </div>
                            <button type="submit" className="btn btn-success">ChangePassword</button>

                        </form>
                    </div>
                </div>
            </div>F
            <ToastContainer position="top-center" autoClose={1500} />
        </div>
    )
}


export default ChangePassword;
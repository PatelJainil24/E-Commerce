import { useEffect, useState } from "react";
import Header from "../common/Header";
import axios from "axios";


const MyProfile = () => {


    const storedUser = localStorage.getItem('user');
    const user = JSON.parse(storedUser);

    let [firstName, setFirstName] = useState("");
    let [lastName, setLastName] = useState("");
    let [email, setEmail] = useState("");
    let [mobile, SetMobile] = useState("");
    let [editMode, setEditMode] = useState(false);
    let [userData, setUserData] = useState(null);


    useEffect(() => {

        if (user) {
            setFirstName(user.firstName || '');
            setLastName(user.lastName || '');
            setEmail(user.email || '');
            SetMobile(user.phone || '');
        }

    }, []);

    const handleSubmit = async () => {
        try {

            const updatedUser = {
                ...user,
                firstName,
                lastName,
                email,
                phone: mobile
            }
            const res = await axios.put(`http://localhost:8050/api/users/${user.userId}`, updatedUser);
            setUserData(res.data);
            localStorage.setItem("user", JSON.stringify(res.data));
            setEditMode(false);
        } catch (err) {
            console.log("Failed To Update Profile", err);

        }
    }

    const handleCancel = () => {
        setEditMode(false);
        if (user) {
            setFirstName(user.firstName || '');
            setLastName(user.lastName || '');
            setEmail(user.email || '');
            SetMobile(user.phone || '');
        }

    }

    return (
        <div className="container-fluid">
            <Header />

            <div className="d-flex justify-content-center align-items-center mt-5">

                <div className="card shadow-lg p-4 border-0 text-center" style={{ maxWidth: "600px", width: "100%" }}>
                    <div className="d-flex flex-column align-items-center mb-4">
                        <div className="rounded-circle bg-success text-white d-flex justify-content-center align-items-center "
                            style={{ width: "90px", height: "90px", fontSize: "32px" }}>
                            {user.firstName?.[0]} {user.lastName?.[0]}
                        </div>
                        <h3 className="fw-bold text-capitalize">
                            {user.firstName} {user.lastName}
                        </h3>
                        <span className="badge bg-danger">
                            {user.credential?.roleBasedAuthority}
                        </span>
                    </div>

                    <div className="text-start">
                        <div className="mb-3">
                            <div className="form-label fw-semibold">
                                <label className="form-label"> <i className="bi bi-person-fill"></i> FirstName</label>
                            </div>
                            {editMode ? (
                                <input type="text" value={firstName} className="form-control" onChange={(e) => setFirstName(e.target.value)} />
                            ) : (
                                <p className="">{user.firstName}</p>
                            )}

                        </div>

                        <div className="mb-3">
                            <div className="form-label fw-semibold">
                                <label className="form-label me-2"><i className="bi bi-person-lines-fill" /> Last Name</label>
                            </div>

                            {editMode ? (
                                <input type="text" value={lastName} className="form-control" onChange={(e) => setLastName(e.target.value)} />
                            ) : (
                                <p className="">{user.lastName}</p>
                            )}

                        </div>

                        <div className="mb-3">
                            <div className="form-label fw-semibold">
                                <label className="form-label"><i class="bi bi-envelope-fill"></i> Email</label>
                            </div>
                            {editMode ? (
                                <input type="text" value={email} className="form-control" onChange={(e) => setEmail(e.target.value)} />
                            ) : (
                                <p className="">{user.email}</p>
                            )}
                        </div>

                        <div className="mb-3">
                            <div className="form-label fw-semibold">
                                <label className="form-label"><i class="bi bi-phone-fill"></i> Phone</label>
                            </div>
                            {editMode ? (
                                <input type="text" value={mobile} className="form-control" onChange={(e) => SetMobile(e.target.value)} />
                            ) : (
                                <p className="">{user.phone}</p>
                            )}

                        </div>

                        <div className="d-flex justify-content-center">

                            {editMode ? (
                                <>
                                    <button className="btn btn-success me-2" onClick={handleSubmit}><i className="bi bi-save"></i> Update
                                    </button>

                                    <button className="btn btn-success me-2" onClick={handleCancel} ><i class="bi bi-x"></i> Cancel
                                    </button>
                                </>

                            ) : (
                                <button className="btn btn-success" onClick={() => setEditMode(true)}><i class="bi bi-pencil-fill"></i> Edit Profile
                                </button>
                            )}

                        </div>
                    </div>
                </div>
            </div >
        </div >
    )
}


export default MyProfile;
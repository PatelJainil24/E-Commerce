import { useState } from "react";
import { validateEmail, validatePassword, validateUserName } from "../Utils/utils";
import Header from "../common/Header";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';

function Signup() {

    // useState : React Hook

    let [firstName,setFirstName] = useState("");
    let [lastName,setLastName] = useState("");
    let [email,setEmail] = useState("");
    let [userName,setUserName] = useState("");
    let [password,setPassword] = useState("");
    let [mobileNumber,setMobileNumber] = useState("");
    let [role,setRole] = useState("ROLE_USER");

    let [firstNameError,setFirstNameError] = useState("");
    let [lastNameError,setLastNameError] = useState("");
    let [emailError,setEmailError] = useState("");
    let [userNameError,setUserNameError] = useState("");
    let [passwordError,setPasswordError] = useState("");
    let [mobileError,setmobileError] = useState("");

    function  handleFirstNameChange(e) {
        setFirstName(e.target.value);
         
    }
    function  handleLastNameChange(e) {
        setLastName(e.target.value);
         
    }
    function  handleEmailChange(e) {
        setEmail(e.target.value);
         
    }
    function  handleUserNameChange(e) {
        setUserName(e.target.value);
        
    }
    function  handlePasswordChange(e) {
        setPassword(e.target.value);
        
    }
    function  handleMobileNumberChange(e) {
        setMobileNumber(e.target.value);
          
    }

    async function handleRegister(e){

        let errorCount = 0;

        if(firstName.length < 3){
             errorCount++;
            setFirstNameError("FirstName should be grater thant 3 characters");
        }else{
            setFirstNameError("");
        }

        if(lastName.length < 1){
             errorCount++;
            setLastNameError("LastName is requried");
        }else{
            setLastNameError("");
        }

        if(!validateEmail(email)){
            errorCount++;
            setEmailError("Invalid email address");
        }else{
            setEmailError("");
        }

        if(!validateUserName(userName)){
            errorCount++;
            setUserNameError("UserName must be aleast 3 characters");
        }else{
            setUserNameError("");
        }

        if(!validatePassword(password)){
            errorCount++;
            setPasswordError("Password must be atleast 8 characters");
        }else{
            setPasswordError("");
        }

        if(mobileNumber.length < 10){
            errorCount++;
            setmobileError("Mobile number must be 10 digits");
        }else{
            setmobileError("");
        }

        if(errorCount >0) return;

        const userRequest = {
            firstName,
            lastName,
            email,
            phone : mobileNumber,
            credential : {
                username :userName,
                password,
                roleBasedAuthority : role
            }
        }

        try {
            let response= await axios.post("http://localhost:8050/api/users",userRequest);
            //console.log(response);
            if(response.status === 201){
                //console.log("User registered successfully...");
                toast.success("User registered successfully...");

                setTimeout(() => {
                    window.location = "/login";
                }, 3000);
            }
            
        } catch (e) {
            //console.log(e);
            if(e.response.status === 500){
                console.log("User already exists...");
                toast.error("User already exists...");
            }else{
                console.log("UnExpected Error...");
                toast.error("UnExpected Error...");
                
            }
        }

    }



    return (

        <div className="container-fluid">
            <Header />
            <div className="row mt-5 justify-content-center ">
                <div className="col-4 border shadow p-3">
                    <h3>Account Signup</h3>
                    
                    <div className="mt-3">
                        <label className="">FirtName</label>
                        <input type="text" className="form-control" placeholder="First name" 
                            onChange={e => handleFirstNameChange(e)}/>
                        <div className="text-danger">{firstNameError}</div>
                    </div>
                    <div className="mt-3">
                        <label className="">LastName</label>
                        <input type="text" className="form-control" placeholder="Last name" 
                            onChange={e => handleLastNameChange(e)}/>
                        <div className="text-danger">{lastNameError}</div>
                    </div>
                    <div className="mt-3">
                        <label className="">Email</label>
                        <input type="text" className="form-control" placeholder="Email Address" 
                            onChange={e => handleEmailChange(e)}/>
                        <div className="text-danger">{emailError}</div>
                    </div>
                    <div className="mt-3">
                        <label className="">UserName</label>
                        <input type="text" className="form-control" placeholder="UserName" 
                            onChange={e => handleUserNameChange(e)}/>
                        <div className="text-danger">{userNameError}</div>
                    </div>
                    <div className="mt-3">
                        <label className="">Password</label>
                        <input type="password" className="form-control" placeholder="Password" 
                            onChange={e => handlePasswordChange(e)}/>
                        <div className="text-danger">{passwordError}</div>
                    </div>
                    <div className="mt-3">
                        <label className="">Mobile Number</label>
                        <input type="number" className="form-control" placeholder="Mobile Number" 
                            onChange={e => handleMobileNumberChange(e)}/>
                        <div className="text-danger">{mobileError}</div>
                    </div>
                    <div className="mt-3">
                        <label>Role</label>
                        <select className="form-select"
                            onChange={e => setRole(e.target.value)}>
                            <option value="ROLE_USER">User</option>
                            <option value="ROLE_ADMIN">Admin</option>
                        </select>
                    </div>

                    <div className="d-grid mt-3">
                        <button className="btn btn-warning" onClick={e=>handleRegister(e)}>Register</button>
                    </div>

                    <div className="d-grid mt-3">
                        <a href="/login" className="btn btn-success">Login</a>
                    </div>
                </div>
            </div>
            <ToastContainer position="top-center" autoClose={3000}/>
        </div>
    )
}

export default Signup;
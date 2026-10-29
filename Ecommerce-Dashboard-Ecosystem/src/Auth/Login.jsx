import { useState } from "react";
import { validatePassword, validateUserName } from "../Utils/utils";
import Header from "../common/Header";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { ToastContainer, toast } from 'react-toastify';






function Login() {

    let [userName, setUserName] = useState("");
    let [password, setPassword] = useState("");

    let [userNameError,setUserNameError] = useState("");
    let [passwordError,setPasswordError] = useState("");

     function  handleUserNameChange(e) {
        setUserName(e.target.value);
        
    }
    function  handlePasswordChange(e) {
        setPassword(e.target.value);
        
    }

    async function handleLogin(e){

        let errors = 0;        

        if(!validateUserName(userName)){
            errors++;
            setUserNameError("UserName must be aleast 3 characters");
        }else{
            setUserNameError("");
        }

        if(!validatePassword(password)){
            errors++;
            setPasswordError("Password must be atleast 8 characters");
        }else{
            setPasswordError("");
        }

        if(errors > 0) return;

        const authRequest = {
            username : userName,
            password : password
        }

        try {
            
            //ES6 Features -Destrucuting Data in javascript
            let response= await axios.post("http://localhost:8059/api/authenticate",authRequest);
            console.log(response);
            const {data,status} = response;
            // console.log(data.jwtToken);
            // console.log(status);

            const decodedToken= jwtDecode(data.jwtToken);
            //console.log(decodedToken.sub);

            const usrname= decodedToken.sub;
            
            const userResponse= await axios.get(`http://localhost:8050/api/users/username/${usrname}`);
            console.log(userResponse.data);
            


            localStorage.setItem("accessToken",data.jwtToken);
            localStorage.setItem("user",JSON.stringify(userResponse.data));
            //sessionStorage.setItem("accessToken",data.jwtToken);
            toast.success("Login Successfull...");
            window.location = "/products"
        } catch (error) {
            
        }
        

        
    }


    return(

        <div className="container-fluid">
            <Header />
            <div className="row mt-5 justify-content-center ">
                <div className="col-4 border shadow p-3">
                <h3>Login Form</h3>
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
                    <div className="d-grid mt-3">
                        <button className="btn btn-warning" onClick={e=>handleLogin(e)}>Login</button>
                    </div>

                    <div className="d-grid mt-3">
                        {/* <button className="btn btn-success">Register</button> */}
                        <a href="/register" className="btn btn-success">Register</a>
                    </div>
                </div>
            </div>
            <ToastContainer position="top-center" autoClose={3000}/>
        </div>
    )
}


export default Login;
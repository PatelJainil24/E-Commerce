import { useNavigate } from 'react-router-dom';
import logo from '../images/logo.png';
import 'bootstrap-icons/font/bootstrap-icons.css';


function Header() {

    const storedUser = localStorage.getItem("user");
    // check : user loggedin
    const user = storedUser ? JSON.parse(storedUser) : null;
    const navigate= useNavigate();

    function handleLogout(){
        localStorage.clear();
        navigate("/login")
    }

    return (
        <nav className="navbar navbar-expand-lg bg-success " >
            <div className="container-fluid">
                <a className="navbar-brand" href="#">
                    {/* <img src="https://d502jbuhuh9wk.cloudfront.net/logos/667e58e59e1d9415b6133855.png?v=4" alt="" width={120} /> */}
                    <img src={logo} width={100} />
                </a>

                <form className="d-flex" role="search">
                    <input className="form-control me-2" type="search"
                        placeholder="Search for Products,Brands and More" aria-label="Search"
                        style={{ width: '600px' }} />

                </form>

                {/* <div className="collapse navbar-collapse" >
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <span className="nav-link active" aria-current="page" href="#">Home</span>
                        </li>
                    </ul>

                    <ul className="navbar-nav">
                        <li className="">
                            <a className="btn btn-outline-success" href="/login">Login</a>
                        </li>
                    </ul>
                </div> */}

                <div className="d-flex justify-content-between w-100" >
                    <ul className="navbar-nav flex-row">
                        <li className="nav-item ">
                            <a href="/cart">
                                <span className="nav-link fw-bold text-white" aria-current="page" href="#"><i class="bi bi-cart-check me-1"></i>Cart</span>
                            </a>
                        </li>
                    </ul>

                    <ul className="navbar-nav flex-row">

                        {
                            user && (
                                <>
                                    {/* Admin Only Links */}
                                    {user.credential?.roleBasedAuthority?.includes("ROLE_ADMIN") && (

                                        <>
                                            <li className="nav-item me-3">
                                                <a className="nav-link fw-bold text-white btn  " href="/addProduct">AddProducts</a>

                                            </li>
                                            <li className="nav-item me-3">
                                                <a className="nav-link fw-bold text-white btn  " href="/users">Users</a>

                                            </li>
                                            <li className="nav-item me-3">
                                                <a className="nav-link fw-bold text-white btn  " href="/shipping">Shipping</a>

                                            </li>
                                        </>
                                    )}
                                    {/* Commong to all loggedin users */}

                                    <li className="nav-item me-3">
                                        <a className="nav-link fw-bold text-white btn  " href="/products">Products</a>

                                    </li>
                                    <li className="nav-item me-3">
                                        <a className="nav-link fw-bold text-white btn  " href="/orderList">My Orders</a>

                                    </li>
                                    <li className="nav-item dropdown me-3">
                                        <a className="nav-link text-light dropdown-item"
                                            href="/"
                                            data-bs-toggle="dropdown">
                                            <i className="bi bi-person-circle me-1"></i>{user?.firstName}</a>
                                        <ul className="dropdown-menu dropdown-menu-end">
                                            <li><a className="dropdown-item" href="/profile">My Profile</a></li>
                                            <li><a className="dropdown-item" href="/changePassword">
                                            Change Password</a></li>
                                            <li><a className="dropdown-item" href="/orderList">My Orders</a></li>
                                            <li><hr claclassNamess="dropdown-divider"></hr></li>
                                            <li><button className="dropdown-item text-danger" 
                                                onClick={handleLogout}>Logout</button></li>
                                        </ul>
                                    </li>

                                </>
                            )
                        }


                        {
                            !user && (
                                <li className="nav-item d-flex gap-2">
                                    <a className="btn btn-outline-light " href="/login"><i className="bi bi-box-arrow-in-right me-1"></i>Login</a>
                                    <a className="btn btn-outline-light " href="/register"><i className="bi bi-person-plus me-1"></i>Register</a>
                                </li>
                            )
                        }


                    </ul>
                </div>


            </div>
        </nav>
    );
}

export default Header;
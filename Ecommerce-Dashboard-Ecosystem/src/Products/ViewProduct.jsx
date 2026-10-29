import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Header from "../common/Header";
import { ToastContainer, toast } from 'react-toastify';
function ViewProduct(){

    const [productId,setProductId] = useState(useParams().id);
    const [product,setProduct] = useState({});
    const navigate= useNavigate();

    // check : user loggedin
    const storedUser = localStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null;

    //Add To Cart:
    const handleAddToCart = async () => {

        if(!user) return navigate('/login');

        const request = {
            userId : user.userId,
            productId : product.id,
            quantity : 1
        }

        try {
            
            const res= await axios.post("http://localhost:8052/cart/add",request);
            toast.success("Login Successfull...");
            navigate('/cart'); 

            
        } catch (err) {
            toast.error("Failed to add item to cart");
        }
    }
    
    useEffect(() => {
        getSingleProduct();
    },[])

    async function getSingleProduct(){
        try {
            const response= await axios.get(`http://localhost:8051/products/image/${productId}`);
            setProduct(response.data);
        } catch (err) {
            console.log("Failed to fetch product : ",err);
            
        }
    }

    // ES6 feature : Arrow function.
    const handleEditProduct = () =>{
        navigate(`/edit-product/${productId}`);
    };

    return(
        <div className="container-fluid">
            <div className="row">
                <div className="col-12">
                    <Header/>
                </div>
            </div>

            <div className="row">
                <div className="col-12">
                    <div className="row">
                        <div className="col-6">
                            <div className="card h-100" style={{ width: "30rem" }}>
                                <img src={product.imageData} alt="" />
                            </div>
                            {/* <img src={product.imageData} alt="" className="img-fluid" /> */}
                        </div>
                        <div className="col-6 border">
                            <h3>{product.name}</h3>
                            <div className="display-4 mt-3">
                                {product.price}

                            </div>
                            <h5 className="mt-4">{product.description}</h5>

                            <button className="btn btn-warning btn-lg w-100 mt-4" onClick={handleAddToCart}>Add to Card</button>
                            
                            {/* Edit Product Only for Admin user */}
                            {user && user?.credential?.roleBasedAuthority === "ROLE_ADMIN" &&(
                                <button onClick={handleEditProduct} className="btn btn-success btn-lg w-100 mt-4">Edit Product</button>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <ToastContainer position="top-center" autoClose={3000}/>
        </div>
    )
}

export default ViewProduct;
import axios from "axios";
import Header from "../common/Header";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";


function ViewCart() {

    // check : user loggedin
    const storedUser = localStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null;

    const [cartItems, setCartItems] = useState([]);

    const navigate= useNavigate();

    useEffect(() => {
        fetchCartItems();
    }, [])


    // Fetch all cartItems : 
    const fetchCartItems = async () => {
        try {
            const res = await axios.get(`http://localhost:8052/cart/${user.userId}`);
            setCartItems(res.data);
        } catch (err) {
            console.log("Failed to fetch cart Item", err);
        }
    }

    //Total Price of Cart
    const getTotalPrice = () => {
        return cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
    }

    //Remove Item:
    const handleRemoveItem = async (productId) => {

        try {
            await axios.delete(`http://localhost:8052/cart/remove/${productId}?userId=${user.userId}`);
            fetchCartItems();
        } catch (err) {
            console.log("Failed To Remove Items..", err);

        }
    }

    //Increase and Decrease Quantity : 
    const handleQuantityChange = async(item,quantity) => {

        if(quantity <1) return ;
        
        const request = {
            userId: user.userId,
            productId: item.productId,
            quantity
        }

        try {
            const res= await axios.put("http://localhost:8052/cart/update",request);
             fetchCartItems();
            
        } catch (err) {
            console.log("Failed to Update Quantity",err);
            
        }

    }

    //CartItems Rendering :
    const renderCartItem = (item) => (
        <div className="card mb-3" key={item.id}>
            <div className="row " >
                <div className="col-3 text-center" >
                    <img src={item.product.imageData} alt="" srcset="" style={{ width: '8rem' }} />
                </div>
                <div className="col-9">
                    <div className="card-body">
                        <h5>
                            {item.product.name}
                        </h5>
                        <p className="card-text">
                            {
                                item.product.description.length > 200 ? item.product.description.substring(0, 200) + "...."
                                    :
                                    item.product.description
                            }
                        </p>
                        <div className="d-flex justify-content-between align-items-center">
                            <div>
                                <h6>Price : {item.product.price}</h6>

                                {/* // RenderQuantity Control */}
                                <div className="d-flex align-items-center">
                                    <h6 className="me-2 ">Quantity:</h6>
                                    <button className="btn btn-outline-secondary btn-sm me-2" onClick={() => handleQuantityChange(item, item.quantity - 1)}>
                                        <i className="bi bi-dash-lg"></i>
                                    </button>
                                    <span>{item.quantity}</span>
                                    <button className="btn btn-outline-secondary btn-sm ms-2" onClick={() => handleQuantityChange(item, item.quantity + 1)}>
                                        <i className="bi bi-plus-lg"></i>
                                    </button>
                                </div>
                                <h6>Total : {item.product.price * item.quantity}</h6>
                            </div>
                            <button className="btn btn-danger" onClick={() => handleRemoveItem(item.productId)}>Remove</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

    //Placed Order:
    const handlePlaceOrder = async() =>{

        const request = {
            userId : user.userId
        }
        try {
            const res= await axios.post('http://localhost:8053/orders',request);
            const orderId = res.data.orderId;
            navigate(`/orderSummary/${orderId}`)

            if(!orderId){
                alert("Failed place the order , Please try again")
            }
            console.log("order Placed,",res.data);
            

        } catch (err) {
            console.log(err);
            
        }
    }

    return (
        <div className="container-fluid">
            <Header />
            <div className="d-flex alien-items-center mb-4 justify-content-between">
                <h3>Cart Items</h3>
                <h4>Total Price:{getTotalPrice()}</h4>
            </div>

            {
                cartItems.length === 0 ? (
                    <div className="text-center mt-4">
                        <img src="https://rukminim2.flixcart.com/www/800/800/promos/16/05/2019/d438a32e-765a-4d8b-b4a6-520b560971e8.png?q=90" alt=""
                            style={{ width: '450px' }} />
                        <h4>Missing the Cart Item</h4>
                        <p>Login to See the Items you added preview</p>
                        <button className="btn btn-danger btn-lg">Shop Now</button>
                    </div>
                ) : (
                    <>
                        {cartItems.map(renderCartItem)}

                        <div className="text-end">
                            <button className="btn btn-success btn-lg" onClick={handlePlaceOrder}>Place Order</button>
                        </div>
                    </>
                )
            }

        </div>
    )
}


export default ViewCart;
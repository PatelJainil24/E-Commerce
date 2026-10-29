import { useNavigate, useParams } from "react-router-dom";
import Header from "../common/Header";
import axios from "axios";
import { useEffect, useState } from "react";


const OrderSummary = () => {

    const { orderId } = useParams();
    const [order, setOrder] = useState(null);
    const navigate  = useNavigate();
    const storedUser = localStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null;

    useEffect(() => {
        fetchOrder();
    }, [orderId]);


    const fetchOrder = async () => {
        try {
            const res = await axios.get(`http://localhost:8053/orders/${orderId}`);
            setOrder(res.data);
            console.log(res.data);

        } catch (err) {
            console.log("Fetch to Failed Order,", err);

        }
    }

    if (!order) {
        return (
            <div className="container-fluid">
                <Header />
                <div className="alert alert-danger">Loading Order Summary .....</div>
            </div>
        )
    }

    const handlePaymentRedirect = () => {
        navigate('/payment', {
            state : {
                orderId : order.orderId,
                amount: order.totalPrice,
                name : user.firstName,
                email : user.email,
                phone : user.phone
            }
        })
    }

    return (
        <div className="container-fluid">
            <Header />
            <h3>OrderSummary</h3>
            <div className="">
                <strong>Order ID:</strong> {orderId} <br />
                <strong>Placed At:</strong> {new Date(order.placedAt).toLocaleString()} <br />
                <strong>Status:</strong>{order.status}  <br />
            </div>

            <ul className="list-group mb-4">
                {order.items.map((item, index) => (
                    <li className="list-group-item mb-3" style={{ background: "rgba(233, 246, 233, 0.5)" }}>
                        <div className="row align-items-center">
                            <div className="col-2 d-flex justify-content-center">
                                <img src={item.product.imageData} alt=""
                                    className="img-thumbnail"
                                    style={{ width: '150px', height: '150px', objectFit: 'cover' }}></img>
                            </div>
                            <div className="col-7">
                                <h6 className="mb-1 fw-semibold">{item.product.name}</h6>
                                <p className="text-muted">
                                    {item.product.description?.substring(0, 100)}...
                                </p>
                            </div>

                            <div className="col-3 text-end">
                                <div><strong>Qty:</strong> {item.quantity}</div>
                                <div><strong>Price:</strong> <i className="bi bi-currency-rupee"></i>{item.price}</div>
                            </div>
                        </div>
                    </li>
                ))}
            </ul>

            <div className=" d-flex justify-content-between align-content-center mb-5">
                <h5 className="">Total Amount: <i className="bi bi-currency-rupee"></i>{order.totalPrice}</h5>

                {
                    order.status === "PLACED" && (
                        <button className="btn btn-success btn-lg" onClick={handlePaymentRedirect}>
                            <i className="bi bi-credit-card"></i> Proceed To Payment
                        </button>
                    )
                }
            </div>
        </div>
    )
}

export default OrderSummary;
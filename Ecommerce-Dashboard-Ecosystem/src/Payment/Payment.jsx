import axios from "axios";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";


const Payment = () => {

    const location = useLocation();
    const storedUser = localStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null;
    const razorpayKey = "rzp_test_l1o6qKkq2Arljp"; // Replace with your actual key
    const navigate = useNavigate();

    // ES6 - Destrucuring : 
    const { orderId, amount, name, email, phone } = location.state || {};


    useEffect(() => {

        if (orderId && amount) {
            startPayment();
        } else {
            navigate("/cart");
        }

    }, [])

    const startPayment = async () => {

        try {
            const response = await axios.post("http://localhost:8080/create_order", {
                name,
                email,
                amount: parseInt(amount),
                orderId: parseInt(orderId),
            })

            const orderData = response.data;

            const options = {
                key: razorpayKey,
                amount: orderData.amount,
                currency: "INR",
                name: name,
                description: "E-Commerce Payment",
                order_id: orderData.id,
                handler: handlePaymentSuccess,
                prefill: {
                    name,
                    email,
                    contact: phone
                },
                theme: {
                    color: "#3399cc",
                },
            };

            const rzp = new window.Razorpay(options);
            rzp.open();


        } catch (err) {
            console.error("Payment error:", error);
            alert("Payment initialization failed.");

        }
    }

    const handlePaymentSuccess = async (response) => {

        try {

            await axios.post("http://localhost:8080/payment_success", null, {
                params: {
                    razorpay_payment_id: response.razorpay_payment_id,
                    razorpay_order_id: response.razorpay_order_id,
                    amount: parseInt(amount)
                }
            });

            //clear the cart : 
            await axios.delete(`http://localhost:8052/cart/clear/${user.userId}`);
            //update order status : 
            await axios.put(`http://localhost:8053/orders/${orderId}/status/CONFIRMED`);
            
            

            navigate("/orderList");

        } catch (err) {
            console.log(err);
        }
    }

    return (
        <div className="container text-center">
            <h4>Redirecting to Payment Gateway</h4>
            <p>Please wait. Do not refresh the page</p>
        </div>
    );
};

export default Payment;
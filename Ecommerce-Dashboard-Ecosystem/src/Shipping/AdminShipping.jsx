import { useEffect, useState } from "react";
import Header from "../common/Header"
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';


// const AdminShipping = () => {

//     const storedUser = localStorage.getItem('user');
//     const user = JSON.parse(storedUser);

//     const [orders, setOrders] = useState([]);
//     const [loading, setLoading] = useState(true);
//     const [activeTab, setActiveTab] = useState("CONFIRMED")


//     useEffect(() => {
//         fetchAllOrders();
//     }, [])

//     const fetchAllOrders = async () => {
//         try {
//             const response = await axios.get("http://localhost:8053/orders");
//             setOrders(response.data);
//             console.log(response.data);

//         } catch (err) {
//             console.log("failde to Fetch Orders : ", err);
//         } finally {
//             setLoading(false);
//         }
//     }

//     const filterOrders = orders.filter(order => order.status === activeTab);

//     const handleShipOrder = async (orderId) => {
//         try {
//             const shippingPayload = {
//                 orderId: orderId,
//                 shippingMethod: "STANDARD",
//                 carrier: "FedEx"
//             }

//             const response = await axios.post(`http://localhost:8055/shipping/ship/${orderId}`, shippingPayload);
//             if (response.data) {
//                 alert("Order Shipped Successfully...");
//                 fetchAllOrders();
//             }
//         } catch (err) {
//             console.log("Failed to shipped Order");

//         }
//     }

//     const handleMarkAsDelivered = async(orderId) => {
//         try {
//            const response= await axios.put(`http://localhost:8055/shipping/update-status/${orderId}?status=DELIVERED`);

//            if(response.data){
//                 alert(`Order ${orderId} marked as Delivered.`);
//                 fetchAllOrders();
//            }
//         } catch (err) {
//             console.log("Failed to Deliverd Order",err);

//         }
//     }

//     return (
//         <div className="container-fluid">
//             <Header />
//             <h3 className="mb-4">Admin - Shipping Panel</h3>

//             {/* Tabs */}

//             <ul className="nav nav-tabs">
//                 <li className="nav-item">
//                     <button className={`nav-link ${activeTab === "CONFIRMED" ? "active" : ""}`}
//                         onClick={() => setActiveTab("CONFIRMED")}>
//                         Confirmed Orders
//                     </button>
//                 </li>
//                 <li className="nav-item">
//                     <button className={`nav-link ${activeTab === "SHIPPED" ? "active" : ""}`}
//                         onClick={() => setActiveTab("SHIPPED")}>

//                         Shipped Orders
//                     </button>
//                 </li>
//             </ul>

//             {/* Loading */}
//             {loading ? (
//                 <div className="alert alert-info">Loading...</div>
//             ) : (
//                 <>
//                     <table className="table table-bordered table-striped">
//                         <thead className="table-dark">
//                             <tr>
//                                 <th>Order Id</th>
//                                 <th>Placed At</th>
//                                 <th>Status</th>
//                                 <th>Total Price</th>
//                                 {(activeTab === 'CONFIRMED' || activeTab === 'SHIPPED') && <th>Action</th>}

//                             </tr>
//                         </thead>
//                         <tbody>
//                             {filterOrders.map(order => (
//                                 <tr key={order.orderId}>
//                                     <td>{order.orderId}</td>
//                                     <td>{new Date(order.placedAt).toLocaleString()}</td>
//                                     <td>{order.status}</td>
//                                     <td>{order.totalPrice}</td>
//                                     {(activeTab === 'CONFIRMED' || activeTab === 'SHIPPED') &&
//                                         <td>
//                                             {activeTab === 'CONFIRMED' &&
//                                                 <button className="btn btn-primary btn-sm"
//                                                     onClick={() => handleShipOrder(order.orderId)}
//                                                 >Ship Order
//                                                 </button>}


//                                             {activeTab === 'SHIPPED' &&
//                                                 <button className="btn btn-success btn-sm"
//                                                 onClick={() => handleMarkAsDelivered(order.orderId)}>
//                                                     Deliver Order
//                                                 </button>}
//                                         </td>
//                                     }
//                                 </tr>
//                             ))}
//                         </tbody>
//                     </table>
//                 </>
//             )}
//         </div>
//     )
// }

function AdminShipping() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState("CONFIRMED");

  const storedUser = localStorage.getItem("user");
  const user = storedUser ? JSON.parse(storedUser) : null;

  useEffect(() => {
    if (user && user.userId) {
      fetchAllOrders();
    }
  }, []);

  const fetchAllOrders = async () => {
    try {
      const response = await axios.get(`http://localhost:8053/orders`);
      setOrders(response.data || []);
    } catch (error) {
      console.error("Failed to fetch orders", error);
      setOrders([]);
    } finally {
      setLoading(false);
    }
  };

  const handleMarkAsDelivered = async (orderId) => {
    try {
      const response = await axios.put(
        `http://localhost:8059/shipping/update-status/${orderId}?status=DELIVERED`
      );

      if (response.data) {
        toast.success(`Order ${orderId} marked as Delivered.`);
        fetchAllOrders(); // refresh list
      } else {
        alert("Failed to update status.");
      }
    } catch (error) {
    
      toast.error(`Failed to Delivered Order Id: ${orderId} !`);

    }
  };


  const handleShipOrder = async (orderId) => {
    try {
      const shippingPayload = {
        orderId: orderId,
        shippingMethod: "STANDARD",
        carrier: "FedEx",
      };

      const response = await axios.post(
        `http://localhost:8055/shipping/ship/${orderId}`,
        shippingPayload
      );

      if (response.data) {
        toast.success(`Order ${orderId} shipped successfully!`);
        fetchAllOrders();
      } else {
        alert("Shipping failed.");
      }
    } catch (error) {
      console.error("Shipping error:", error);
      alert("Error shipping order.");
    }
  };

  const filteredOrders = orders.filter((order) => order.status === activeTab);

  return (
    <div className="container-fluid mt-4">
      <Header />
  
      <h3 className="mb-4">Admin - Shipping Panel</h3>

      {/* Tabs */}
      <ul className="nav nav-tabs">
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "CONFIRMED" ? "active" : ""}`}
            onClick={() => setActiveTab("CONFIRMED")}
          >
            Confirmed Orders
          </button>
        </li>
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "SHIPPED" ? "active" : ""}`}
            onClick={() => setActiveTab("SHIPPED")}
          >
            Shipped Orders
          </button>
        </li>
       
      </ul>

      {/* Table */}
      {loading ? (
        <div className="alert alert-info">Loading orders...</div>
      ) : filteredOrders.length === 0 ? (
        <div className="alert alert-warning">No orders in this category.</div>
      ) : (
        <table className="table table-bordered table-striped">
          <thead className="table-dark">
            <tr>
              <th>Order ID</th>
              
              <th>Placed At</th>
              <th>Status</th>
              <th>Total Price</th>
              {(activeTab === "CONFIRMED" || activeTab === "SHIPPED") && <th>Action</th>}

            </tr>
          </thead>
          <tbody>
            {filteredOrders.map((order) => (
              <tr key={order.orderId}>
                <td>{order.orderId}</td>
               
                <td>{new Date(order.placedAt).toLocaleString()}</td>
                <td>
                  <span className={`badge ${order.status === 'SHIPPED' ? 'bg-secondary' : 'bg-success'}`}>
                    {order.status}
                  </span>
                </td>
                <td>₹{order.totalPrice}</td>
               
                {(activeTab === "CONFIRMED" || activeTab === "SHIPPED") && (
                  <td>
                    {activeTab === "CONFIRMED" && (
                      <button
                        className="btn btn-primary btn-sm"
                        onClick={() => handleShipOrder(order.orderId)}
                      >
                        Ship Order
                      </button>
                    )}
                    {activeTab === "SHIPPED" && (
                      order.status === "SHIPPED" ? (
                        <button
                          className="btn btn-outline-success btn-sm"
                          onClick={() => handleMarkAsDelivered(order.orderId)}
                        >
                          📦 Mark as Delivered
                        </button>
                      ) : (
                        <span className="badge bg-success">✅ Delivered</span>
                      )
                    )}
                  </td>
                )}

              </tr>
            ))}
          </tbody>
        </table>
      )}
      <ToastContainer position="top-center" autoClose={3000} />
    </div>
  );
}

export default AdminShipping;
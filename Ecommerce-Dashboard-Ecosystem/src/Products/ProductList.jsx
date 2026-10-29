import axios from "axios";
import { useEffect, useState } from "react";
import Header from "../common/Header";
import { Link, useLocation, useNavigate } from "react-router-dom";


// function ProductList() {

//     const [products, setProducts] = useState([]);
//      const navigate = useNavigate();

//     const [filter, setFilter] = useState({
//         name,
//         category,
//         price,
//         available,
//         brand
//     });

//     const location = useLocation();
//     const queryParams = new URLSearchParams(location.search);
//     const name = queryParams.get('name') || "";
//     const category = queryParams.get('category') || "";
//     const price = queryParams.get('price') || "";
//     const available = queryParams.get('available') || "";
//     const brand = queryParams.get('brand') || "";

//     useEffect(() => {
//         fetchProducts();
//     }, [location.search])

//     async function fetchProducts() {
//         try {

//             const params = new URLSearchParams(location.search); // ?name=abc&category=xyz
//             let response;

//             if (params.toString() !== "") {
//                 response = await axios.get(`http://localhost:8051/products/filter?${params.toString()}`);
//             } else {
//                 response = await axios.get('http://localhost:8051/products/image');
//             }
//             //const response = await axios.get('http://localhost:8051/products/image');


//             setProducts(response.data);
//             //console.log(response.data);
//             //console.log("products", products);


//         } catch (error) {
//             console.log("error fetching products", error);

//         }
//     }

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setFilter({ ...filter, [name]: value });
//     }

//     const handleClear = () => {
//         setFilter({
//             name: "",
//             category: "",
//             price: "",
//             available: "",
//             brand: ""
//         });
//     }


//     const handleFilterSubmit = async (e) => {

//         const newParams = new URLSearchParams(); // ?name=abc&category=xyz
//         Object.entries(filter).forEach(([key, value]) => {
//             if (value && value !== "") {
//                 newParams.append(key, value);
//             }
//         });
//         navigate(`/products?${newParams.toString()}`);
//     }



//     return (
//         <div className="container-fluid">
//             <div className="row">
//                 <Header />
//             </div>
//             <div className="row">
//                 <div className="col-2">
//                     <div className="card p-3">
//                         <h4>Filters</h4>
//                         <div className="mb-3">
//                             <label className="form-label">Product Name</label>
//                             <input type="text" name="name" className="form-control" value={filter.name}
//                                 onChange={handleChange} />
//                         </div>

//                         <div className="mb-3">
//                             <label className="form-label">Price</label>
//                             <input type="number" name="price" className="form-control" value={filter.price}
//                                 onChange={handleChange} />
//                         </div>

//                         <div className="mb-3">
//                             <label className="form-label">Brand</label>
//                             <input type="text" name="brand" className="form-control" value={filter.brand}
//                                 onChange={handleChange} />
//                         </div>

//                         <div className="">
//                             <button className="btn btn-primary" onClick={handleClear}>Clear</button>
//                             <button className="btn btn-secondary ms-3" onClick={handleFilterSubmit}>Apply</button>
//                         </div>
//                     </div>
//                 </div>
//                 <div className="col-10">

//                     <div className="row">

//                         {products.length > 0 ? (

//                             products.map((product, index) => (

//                                 <div className="col-4 mb-4" key={index}>

//                                     <Link to={`/viewProduct/${product.id}`} className="text-decoration-none">
//                                         {/* card */}
//                                         <div className="card h-100" style={{ width: "22rem" }}>
//                                             {
//                                                 product.imageData && (
//                                                     <img src={product.imageData}
//                                                         className="card-img-top" alt="..."
//                                                         style={{ objectFit: "cover" }}>
//                                                     </img>
//                                                 )
//                                             }
//                                             <div className="card-body">
//                                                 <h5 className="card-title">{product.name}</h5>
//                                                 <p className="card-text">
//                                                     {
//                                                         product.description.length > 70 ? product.description.substring(0, 70) + "...."
//                                                             :
//                                                             product.description
//                                                     }
//                                                 </p>
//                                                 <p className="card-text fw-bold">{product.price}</p>
//                                                 <p className="card-text">{product.stock}</p>

//                                             </div>
//                                         </div>
//                                     </Link>

//                                 </div>
//                             ))
//                         ) : (
//                             <div>failed to fetch</div>
//                         )}
//                     </div>

//                 </div>
//             </div>
//         </div>
//     )
// }



function ProductList() {

    const [products, setProducts] = useState([]);

    const location = useLocation();
    const navigate = useNavigate();
    const queryParams = new URLSearchParams(location.search);
    const name  = queryParams.get('name') || "";
    const category = queryParams.get('category') || "";
    const price = queryParams.get('price') || "";
    const available = queryParams.get('available') || "";
    const brand  = queryParams.get('brand') || "";


    const [filter, setFilter] = useState({
        name,
        category,
        price,
        available,
        brand
    });

    useEffect(() => {
        fetchProducts();
    }, [location.search]);


    async function fetchProducts() {
        try {

            const params = new URLSearchParams(location.search); // ?name=abc&category=xyz
            let response;

            if(params.toString() !== "") {
                response = await axios.get(`http://localhost:8051/products/filter?${params.toString()}`);
            } else {
                response = await axios.get('http://localhost:8051/products/image');
            }

            //const response = await axios.get('http://localhost:8051/products/image');
            setProducts(response.data);
            console.log(response.data);
        } catch (err) {
            console.log("Error fetching Products", err);
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilter({ ...filter, [name]: value });
    }

    const handleClear = () => {
        setFilter({
            name: "",
            category: "",
            price: "",
            available: "",
            brand: ""
        });
    }

    const handleFilterSubmit = async (e) => {
       
            const newParams = new URLSearchParams(); // ?name=abc&category=xyz
            Object.entries(filter).forEach(([key, value]) => {
                if (value && value !== "") {
                    newParams.append(key, value);
                }   
            });
            navigate(`/products?${newParams.toString()}`);

    };

    return (
        <div className="container-fluid">
            <div className="row">

                <div className="col-12">
                    <Header />
                </div>
            </div>

            <div className="row">
                <div className="col-2">
                    <div className="card p-3">
                        <h4>Filters</h4>
                        <div className="mb-3">
                            <label className="form-label">Product Name</label>
                            <input type="text" name="name" className="form-control" value={filter.name} 
                                onChange={handleChange} />
                        </div>

                        <div className="mb-3 ">
                            <label className="form-label">Price</label>
                            <input type="number" name="price" className="form-control" value={filter.price} 
                                onChange={handleChange} />
                        </div>

                         <div className="mb-3">
                            <label className="form-label">Brand</label>
                            <input type="text" name="brand" className="form-control" value={filter.brand} 
                                onChange={handleChange} />
                        </div>

                        <div className="">
                            <button className="btn btn-primary" onClick={handleClear}>Clear</button>
                            <button className="btn btn-secondary ms-3" onClick={handleFilterSubmit}>Apply</button>
                        </div>
                    </div>
                </div>

                <div className="col-9 mt-3">
                    <div className="row">
                        {products.length > 0 ? (

                            products.map((product, index) => (
                                <div className="col-4 mb-4" key={index}>

                                    <Link to={`/viewProduct/${product.id}`} className="text-decoration-none">
                                    <div className="card h-100">
                                        {
                                            product.imageData && (
                                                <img src={product.imageData} alt="" className="card-img-top" style={{ height: "200px", objectFit: "cover" }}></img>
                                            )
                                        }
                                          <div className="card-body">
                                        <h5 className="card-title">{product.name}</h5>
                                        <p className="card-text">

                                            {

                                                product.description.length > 200 ? product.description.substring(0, 200) + "...." : product.description

                                            }
                                        </p>
                                        <p className="card-text fw-bold"><strong>{product.price}</strong></p>
                                        <p className="card-text">Stock: {product.stock}</p>
                                    </div>
                                    </div>
                                    </Link>
                                  
                                </div>
                            ))

                        ) : (
                            <div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProductList;
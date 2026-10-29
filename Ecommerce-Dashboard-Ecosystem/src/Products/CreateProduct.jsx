import axios from "axios";
import Header from "../common/Header";
import { useEffect, useState } from "react";
import { ToastContainer, toast } from 'react-toastify';
import { useNavigate, useParams } from "react-router-dom";


function CreateProduct() {

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [stock, setStock] = useState('');
    const [image, setImage] = useState(null);

    const { id } = useParams();

    const navigate = useNavigate();

    const isEditMode = Boolean(id);
    console.log(isEditMode);

    useEffect(() => {
        if (isEditMode) {
            getSingleProduct();
        }
    }, [])

    // Fetch data for editing :
    async function getSingleProduct() {
        try {
            const response = await axios.get(`http://localhost:8051/products/image/${id}`);
            const product = response.data;
            setName(product.name);
            setDescription(product.description);
            setPrice(product.price);
            setStock(product.stock);
            console.log(product);

        } catch (err) {
            console.log("Failed to fetch product : ", err);

        }
    }


    // Submit the data to database : 
    async function handleSubmit(e) {
        e.preventDefault();

        if (isEditMode) {
            await editProduct();
        }
        else {
            await addProduct();
        }



        async function addProduct() {
            const formData = new FormData();
            formData.append('name', name);
            formData.append('description', description);
            formData.append('price', price);
            formData.append('stock', stock);
            formData.append('status', "ACTIVE");
            if (image) {
                formData.append('image', image);
            }


            try {
                await axios.post("http://localhost:8051/products/image", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                toast.success("Product Added Successfully");


                setTimeout(() => {
                    window.location = "/products";
                }, 1500);
                console.log("after locate");
            } catch (error) {
                toast.error("Failed to add Product");
                console.log(error);


            }
        }

        async function editProduct() {
            const productData = {
                name,
                description,
                stock,
                price,
                status: "ACTIVE"
            };

            console.log(productData);


            try {

                await axios.put(`http://localhost:8051/products/image/${id}`, productData);
                toast.success("Product Updated Successfully");
                setTimeout(() => {
                    navigate(`/products`);
                }, 1500);
            } catch (err) {
                toast.error("Failed to add Product");
                console.log(err);

            }
        }
    }

    return (
        <div className="container-fluid">
            <Header />

            <div className="row justify-content-center">
                <div className="col-5 card shadow-lg rounded border-0 p-4 mt-4">

                    <h4 className="text-center text-success mb-4">
                        {
                            isEditMode ? "Edit Product" : "Add New Producut"
                        }
                    </h4>

                    <form onSubmit={handleSubmit}>
                        <div className="form-floating">
                            <input type="text" placeholder="Product Name" value={name}
                                className="form-control" required
                                onChange={(e) => setName(e.target.value)} />
                            <label htmlFor="">Product Name</label>
                        </div>
                        <div className="form-floating mt-3">
                            <textarea type="text" placeholder="Description" value={description}
                                className="form-control" required
                                onChange={(e) => setDescription(e.target.value)} />
                            <label htmlFor="">Description</label>
                        </div>
                        <div className="form-floating mt-3">

                            <input type="text" placeholder="price" value={price}
                                className="form-control" required
                                onChange={(e) => setPrice(e.target.value)} />

                            <label htmlFor="">Price</label>
                        </div>
                        <div className="form-floating mt-3">
                            <input type="text" placeholder="stock" value={stock}
                                className="form-control" required
                                onChange={(e) => setStock(e.target.value)} />
                            <label htmlFor="">Stock</label>
                        </div>

                        {/* isEditMode active hide the Image feature */}
                        {
                            !isEditMode && (

                                <div className=" mt-3">
                                    <label htmlFor="">Product Image</label>
                                    <input type="file" className="form-control" accept="image/*" onChange={(e) => setImage(e.target.files[0])} />
                                    {/* accept : only allowed the Imgaes as a file... */}

                                </div>
                            )}

                        <div className="mt-3 d-grid">
                            <button type="submit" className="btn btn-success rounded-pill py-2">
                                {
                                    isEditMode ? "Update Product" : "Add Product"
                                }
                            </button>
                        </div>

                    </form>
                </div>
            </div>
            <ToastContainer position="top-center" autoClose={1500} />
        </div>
    )
}

export default CreateProduct;
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import Signup from './Auth/Signup.jsx'
import Login from './Auth/Login.jsx'
import { BrowserRouter,Routes,Route, Router } from 'react-router-dom'
import ViewCart from './Cart/ViewCart.jsx'

import ProductList from './Products/ProductList.jsx'
import ViewProduct from './Products/ViewProduct.jsx'
import CreateProduct from './Products/CreateProduct.jsx'
import UserList from './Users/UserList.jsx'
import ChangePassword from './Users/ChangePassword.jsx'
import OrderSummary from './Order/OrderSummary.jsx'
import Payment from './Payment/Payment.jsx'
import OrderList from './Order/OrderList.jsx'
import MyProfile from './Auth/MyProfile.jsx'
import AdminShipping from './Shipping/AdminShipping.jsx'


createRoot(document.getElementById('root')).render(
  <StrictMode>


    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Signup/>}></Route>
      <Route path='/register' element={<Signup/>}></Route>
      <Route path='/login' element={<Login/>}></Route>
      <Route path='/cart' element={<ViewCart/>}></Route>
      <Route path='/addProduct' element={<CreateProduct/>}></Route>
      <Route path='/products' element={<ProductList/>}></Route>
      <Route path='/viewProduct/:id' element={<ViewProduct/>}></Route>
      <Route path='/edit-product/:id' element={<CreateProduct/>}></Route>
      <Route path='/users' element={<UserList/>}></Route>
      <Route path='/changePassword' element={<ChangePassword/>}></Route>
      <Route path='/orderSummary/:orderId' element={<OrderSummary/>}></Route>
      <Route path="/payment" element={<Payment />}></Route>
      <Route path="/orderList" element={<OrderList />}></Route>
      <Route path="/profile" element={<MyProfile />}></Route>
      <Route path="/shipping" element={<AdminShipping />}></Route>
    </Routes>

    </BrowserRouter>
  </StrictMode>,
)

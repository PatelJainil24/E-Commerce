

function validatePassword(password){

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,}$/;

    return passwordRegex.test(password);
}
function validateUserName(userName){

    const userNameRegex = /^[a-zA-Z0-9_]{3,20}$/;

    return userNameRegex.test(userName);
}

function validateEmail(email){

    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    return emailRegex.test(email);
}




export {validatePassword,validateUserName,validateEmail};
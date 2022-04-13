const sign_in = document.getElementById("sign_in");
const error = document.getElementById("signIn_error");

sign_in.addEventListener('click', () => {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch("/login",{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "username": username,
            "password": password,
        }),
    }).then((response) =>{
        console.log(response);

        if (response.ok){
            console.log("로그인 성공");
        }else{
            console.log("로그인 실패");
            error.style.display = "block";
        }

    }).catch((error) => {
        console.log(error);
    });
});

submit
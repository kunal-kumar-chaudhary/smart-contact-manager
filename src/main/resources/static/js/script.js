
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    if (theme) return theme
    else return "light";
}

let currentTheme = getTheme();
changeTheme(currentTheme);

function changeTheme(currentTheme){

    
    // setting the listener to change theme button
    const changeThemeButton = document.querySelector("#theme_change_button");

    changeThemeButton.addEventListener("click", () => {
        const oldTheme = currentTheme;

        if (currentTheme === "dark"){
            currentTheme = "light";
        }
        else{
            currentTheme = "dark";
        }
 
        setTheme(currentTheme);

        document.querySelector("html").classList.remove(oldTheme);

        // set the current theme
        document.querySelector("html").classList.add(currentTheme);

        // change the text of button
        changeThemeButton.querySelector("span").textContent = currentTheme == "dark" ? "Light" : "Dark";

    })
    
}

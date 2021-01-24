import logout from "../../lib/utils/logout";

const Logout = ({ res }) => {

    logout();

    return (
        <div>
            You are logout, redirecting to main page
        </div>
    )
}

export default Logout
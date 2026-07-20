import React from "react";
import { Route, Redirect } from "react-router-dom";


function ProtectedRoute({ component: Component, allowedRole, ...rest }) {

    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");


    return (
        <Route
            {...rest}
            render={(props) => {

                if (allowedRole === null) {

                    if (token) {

                        if (role === "ROLE_PATIENT")
                            return <Redirect to="/patient" />;

                        if (role === "ROLE_DOCTOR")
                            return <Redirect to="/doctor" />;
                    }

                    return <Component {...props}/>;
                }


                if (!token) {
                    return <Redirect to="/" />;
                }


                const hasAccess = Array.isArray(allowedRole)
                    ? allowedRole.includes(role)
                    : role === allowedRole;


                if (!hasAccess) {

                    if(role === "ROLE_PATIENT")
                        return <Redirect to="/patient"/>;

                    if(role === "ROLE_DOCTOR")
                        return <Redirect to="/doctor"/>;
                }


                return <Component {...props}/>;

            }}
        />
    );
}


export default ProtectedRoute;
import React, {useEffect} from "react";
import {Navigate, useLocation, useNavigate} from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

interface PrivateRouterProps extends React.PropsWithChildren {
    requiredRole?: 'ADMIN' | string;
}

export function PrivateRoute({ children, requiredRole }: PrivateRouterProps) {
    const { isLoggedIn, user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        // 권한이 없으면 경고를 띄우고 메인 페이지로 이동.
        if (isLoggedIn && requiredRole && user?.role !== requiredRole) {
            alert('접근 권한이 없습니다.');
            navigate('/', { replace: true });
        }
    }, [isLoggedIn, user, requiredRole, navigate]);

    if (!isLoggedIn) {
        // 로그인을 안했으면, 어디로 가려고 했는지 위치를 기억시켜주고 로그인 페이지로 보냄.
        // Navigate 컴포넌트를 사용해서 로그인 페이지로 보냄
        // 'replace' 옵션은 브라우저 히스토리에 현재 경로를 남기지 않아서 뒤로가기 했을 때 다시 이 페이지로 돌아오는 걸 막음.
        // 'state' 옵션으로 로그인 후 돌아올 경로를 알려줌
        alert('로그인이 필요한 페이지입니다.');
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    if (requiredRole && user?.role !== requiredRole) {
        return <div>권한 확인 중...</div>;
    }

    return <>{children}</>;
}

export default PrivateRoute;
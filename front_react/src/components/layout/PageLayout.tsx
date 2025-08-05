import React from 'react';

// children prop의 타입을 명시해줍니다.
interface PageLayoutProps {
    children: React.ReactNode;
}

function PageLayout({ children }: PageLayoutProps) {
    // 모든 페이지 콘텐츠는 자동으로 .section 클래스로 감싼다.
    return (
        <div className="section">
            {children}
        </div>
    );
}

export default PageLayout;

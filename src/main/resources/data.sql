-- 회원 테이블 생성.
CREATE TABLE IF NOT EXISTS users (
    userno          BIGINT  PRIMARY KEY AUTO_INCREMENT,
    id              NVARCHAR(30)    NOT NULL    UNIQUE ,
    pw              NVARCHAR(30)    NOT NULL ,
    name            NVARCHAR(30)     NOT NULL ,
    addr            NVARCHAR(255)    NOT NULL ,
    sleepflag       BOOLEAN         NOT NULL    DEFAULT FALSE ,

    regdate         VARCHAR(19)     NOT NULL ,
    upddate         VARCHAR(19)
);

-- jwt 토큰 테이블 생성.
CREATE TABLE IF NOT EXISTS AUTHTOKEN (
    userid          NVARCHAR(30) UNIQUE ,
    accesstoken     varchar(1000) ,
    refreshtoken    varchar(1000),

    FOREIGN KEY (userid) REFERENCES USERS(id)
);

-- 상품 테이블 생성.
CREATE TABLE IF NOT EXISTS productinfo (
    prodseqno   BIGINT  PRIMARY KEY AUTO_INCREMENT,
    prodname    NVARCHAR(255)   NOT NULL ,
    price       NVARCHAR(255)   NOT NULL ,
    provider    NVARCHAR(255)   NOT NULL ,
    info        NVARCHAR(MAX) ,

    useflag     BOOLEAN         NOT NULL ,
    regdate     VARCHAR(19)     NOT NULL ,
    upddate     VARCHAR(19)
);

-- 주문 테이블 생성.
CREATE TABLE IF NOT EXISTS ORDERINFO (
    ORDERNO     BIGINT          PRIMARY KEY ,
    USERID      NVARCHAR(30)    NOT NULL ,
    USERNAME    NVARCHAR(30)    NOT NULL ,
    USERADDR    NVARCHAR(255)   NOT NULL ,
    PRODSEQNO   BIGINT          NOT NULL ,

    PRODNAME    VARCHAR(255)    NOT NULL ,
    PRODPRICE   NVARCHAR(255)   NOT NULL ,
    REGDATE     VARCHAR(19)     NOT NULL ,
    UPDDATE     VARCHAR(19) ,

    FOREIGN KEY (USERID) REFERENCES USERS(id),
    FOREIGN KEY (PRODSEQNO) REFERENCES productinfo(prodseqno)
);

-- 배송지 테이블 생성.
CREATE TABLE IF NOT EXISTS DELIVERYADDR (
    DELIADDRNO  BIGINT          PRIMARY KEY AUTO_INCREMENT ,
    USERID      NVARCHAR(30)    NOT NULL ,
    ADDRALIAS   NVARCHAR(10)    NOT NULL ,
    DELIADDR    NVARCHAR(255)   NOT NULL ,
    DEFDELIADDR BOOLEAN         NOT NULL DEFAULT FALSE,

    FOREIGN KEY (USERID)    REFERENCES USERS(ID)
);

-- 회원 데이터 삽입.
INSERT INTO users (id, PW, name, addr, regdate)
SELECT *
FROM (SELECT 'test1' AS id, '1234' AS PW, 'John Doe' AS name, '경기도 용인시 어쩌구 저쩌구' AS addr, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') AS REGDATE UNION ALL
      SELECT 'test2', '1234', 'Jane Smith', '경기도 화성시 어쩌구 저쩌구', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT 'test3', '1234', 'Alice Johnson', '경기도 성남시 어쩌구 저쩌구', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT 'test4', '1234', 'Bob Brown', '서울 중랑구 어쩌구 저쩌구', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT 'test5', '1234', 'Charlie Green', '경기도 구리시 어쩌구 저쩌구', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss')) AS temp
WHERE NOT EXISTS (SELECT 1 FROM users);

-- 상품 데이터 삽입.
INSERT INTO productinfo (prodname, price, provider, info, useflag, regdate)
SELECT *
FROM (SELECT '티셔츠' AS prodname, '25000' AS price, '마프' AS provider, '평범한 티 입니다.' AS info, true as useflag, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') AS regdate UNION ALL
      SELECT '티셔츠1', '25000', '마프', '평범한 티 입니다.', true, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT '티셔츠2', '25000', '마프', '평범한 티 입니다.', true, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT '티셔츠3', '25000', '마프', '평범한 티 입니다.', true, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT '티셔츠4', '25000', '마프', '평범한 티 입니다.', true, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss')) AS temp
WHERE NOT EXISTS (SELECT 1 FROM productinfo);
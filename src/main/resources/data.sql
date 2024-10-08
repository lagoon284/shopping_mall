-- 회원 테이블 생성.
CREATE TABLE IF NOT EXISTS users (
    id NVARCHAR(30) PRIMARY KEY ,
    name VARCHAR(30) NOT NULL,
    sleepFrag boolean default false not null
);

-- 회원 데이터 삽입.
INSERT INTO users (id, name)
SELECT *
FROM (SELECT 'test1' AS id, 'John Doe' AS name UNION ALL
      SELECT 'test2', 'Jane Smith' UNION ALL
      SELECT 'test3', 'Alice Johnson' UNION ALL
      SELECT 'test4', 'Bob Brown' UNION ALL
      SELECT 'test5', 'Charlie Green') AS temp
WHERE NOT EXISTS (SELECT 1 FROM users);

-- 상품 테이블 생성
CREATE TABLE IF NOT EXISTS productinfo (
    seqno BIGINT PRIMARY KEY AUTO_INCREMENT,
    prodno VARCHAR(8) NOT NULL UNIQUE ,
    name VARCHAR(255) NOT NULL ,
    price VARCHAR(255) NOT NULL ,
    provider NVARCHAR(255) NOT NULL ,
    info NVARCHAR(MAX),
    regdate VARCHAR(19) NOT NULL ,
    upddate VARCHAR(19)
);

INSERT INTO productinfo (prodno, name, price, provider, info, regdate)
SELECT *
FROM (SELECT '00000001' AS prodno, '티셔츠' AS name, '25000' AS price, '마프' AS provider, '평범한 티 입니다.' AS info, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') AS regdate UNION ALL
      SELECT '00000002', '티셔츠1', '25000', '마프', '평범한 티 입니다.', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT '00000003', '티셔츠2', '25000', '마프', '평범한 티 입니다.', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT '00000004', '티셔츠3', '25000', '마프', '평범한 티 입니다.', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') UNION ALL
      SELECT '00000005', '티셔츠4', '25000', '마프', '평범한 티 입니다.', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss')) AS temp
WHERE NOT EXISTS (SELECT 1 FROM productinfo);
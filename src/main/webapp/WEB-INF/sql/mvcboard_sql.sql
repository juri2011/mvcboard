create table mvcboard(
    idx number primary key,
    name varchar2(50) not null,
    title varchar2(200) not null,
    content varchar2(2000) not null,
    postdate date default sysdate,
    ofile varchar2(200),
    sfile varchar2(30),
    downcount number default 0 not null,
    pass varchar2(50) not null,
    visitcount number default 0 not null
);

desc mvcboard;

-- Oracle에서는 하나의 시퀀스를 둘 이상의 테이블에서 사용할 수 있다.
drop sequence seq_board_num;
create sequence seq_board_num;

-- 더미 데이터 입력
insert into mvcboard (idx, name, title, content, pass)
    values(seq_board_num.nextval, '김유신', '자료실 제목 1입니다.', '내용', '1234');
insert into mvcboard (idx, name, title, content, pass)
    values(seq_board_num.nextval, '장보고', '자료실 제목 2입니다.', '내용', '1234');
insert into mvcboard (idx, name, title, content, pass)
    values(seq_board_num.nextval, '이순신', '자료실 제목 3입니다.', '내용', '1234');
insert into mvcboard (idx, name, title, content, pass)
    values(seq_board_num.nextval, '강감찬', '자료실 제목 4입니다.', '내용', '1234');
insert into mvcboard (idx, name, title, content, pass)
    values(seq_board_num.nextval, '대조영', '자료실 제목 5입니다.', '내용', '1234');
    
select * from mvcboard;

-- 리스트
select * from (
    -- tb는 서브쿼리 결과의 alias
    select Tb.*, ROWNUM rNum FROM (
        SELECT * from mvcboard 
        WHERE title LIKE '%제목%'
        ORDER BY idx DESC
    ) Tb
)
-- 여기서 페이지 정보를 가져온다.(시작게시글, 끝 게시글)
WHERE rNum BETWEEN 1 AND 10;


commit;
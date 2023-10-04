package com.example.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// @Entity 속성을 통해 테이블 및 컬럼을 생성 하는 TodoModel 클래스 생성
@Entity(tableName = "tb_todo")  //table 명
public class TodoModel {
    @PrimaryKey(autoGenerate = true)
    ///@PrimaryKey 의 autoGenerate = : null 을 받으면 id 값을 자동으로 할당해준다.
    private Long id;

    @ColumnInfo(name = "seq")   ///@ColumnInfo : 컬럼명 지정. 컬럼명을 변수명과 같이 쓰려면 생략 가능
    private int seq;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "createDate")
    private long createDate;

        public TodoModel(Long id, int seq, String title, String content, long createDate) {
        this.id = id;
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }
//    public TodoModel() {
//        this.id = null;
//        this.seq = 0;
//        this.title = "";
//        this.content = "";
//        this.createDate = -1;
//    }

    public TodoModel() {
        this(null, 0, "", "", -1);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}

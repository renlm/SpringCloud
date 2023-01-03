-- 分布式事物测试表2
DROP TABLE IF EXISTS "tx2";
CREATE TABLE "tx2"(
    "id" BIGSERIAL PRIMARY KEY,
    "m" INT NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP
)WITH(OIDS=FALSE);
COMMENT ON TABLE "tx2" IS '分布式事物测试表2';
COMMENT ON COLUMN "tx2"."id" IS '主键ID';
COMMENT ON COLUMN "tx2"."m" IS '数字';
COMMENT ON COLUMN "tx2"."created_at" IS '创建时间';
COMMENT ON COLUMN "tx2"."updated_at" IS '更新时间';

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS public.undo_log
(
    id            SERIAL       NOT NULL,
    branch_id     BIGINT       NOT NULL,
    xid           VARCHAR(128) NOT NULL,
    context       VARCHAR(128) NOT NULL,
    rollback_info BYTEA        NOT NULL,
    log_status    INT          NOT NULL,
    log_created   TIMESTAMP(0) NOT NULL,
    log_modified  TIMESTAMP(0) NOT NULL,
    CONSTRAINT pk_undo_log PRIMARY KEY (id),
    CONSTRAINT ux_undo_log UNIQUE (xid, branch_id)
);

CREATE SEQUENCE IF NOT EXISTS undo_log_id_seq INCREMENT BY 1 MINVALUE 1 ;
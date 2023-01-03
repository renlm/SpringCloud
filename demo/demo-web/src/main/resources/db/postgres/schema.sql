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
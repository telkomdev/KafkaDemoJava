.PHONY : gen

SRC_DIR=src/main/proto
DST_DIR=src/main/java/com/telkomdev/producer/protojava

gen:
	protoc -I=$(SRC_DIR) --java_out=$(DST_DIR) $(SRC_DIR)/*.proto
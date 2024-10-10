CC=gcc
CFLAGS=-Wall -Wextra -lcjson

# Đặt thư mục chứa các file .o
BUILD_DIR=../build
DST_DIR=../dst
LOG_FILE=../sys.log
PID_FILE=../server.pid

# Danh sách các file object lưu vào thư mục build
OBJS=$(BUILD_DIR)/server.o $(BUILD_DIR)/net.o $(BUILD_DIR)/file.o $(BUILD_DIR)/mime.o $(BUILD_DIR)/cache.o $(BUILD_DIR)/hashtable.o $(BUILD_DIR)/llist.o

# Đảm bảo thư mục build và dst tồn tại trước khi biên dịch
all: $(BUILD_DIR) $(DST_DIR) $(DST_DIR)/server

# Tạo server từ các file object trong build và lưu vào thư mục dst
$(DST_DIR)/server: $(OBJS)
	$(CC) -o $@ $^ $(CFLAGS)

# Quy tắc để tạo thư mục build nếu nó chưa tồn tại
$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)
$(DST_DIR):
	mkdir -p $(DST_DIR)

# Định nghĩa cách biên dịch các file .o và lưu vào thư mục build
$(BUILD_DIR)/net.o: net.c ../include/net.h
	$(CC) $(CFLAGS) -c net.c -o $@

$(BUILD_DIR)/server.o: server.c ../include/net.h
	$(CC) $(CFLAGS) -c server.c -o $@

$(BUILD_DIR)/file.o: file.c ../include/file.h
	$(CC) $(CFLAGS) -c file.c -o $@

$(BUILD_DIR)/mime.o: mime.c ../include/mime.h
	$(CC) $(CFLAGS) -c mime.c -o $@

$(BUILD_DIR)/cache.o: cache.c ../include/cache.h
	$(CC) $(CFLAGS) -c cache.c -o $@

$(BUILD_DIR)/hashtable.o: hashtable.c ../include/hashtable.h
	$(CC) $(CFLAGS) -c hashtable.c -o $@

$(BUILD_DIR)/llist.o: llist.c ../include/llist.h
	$(CC) $(CFLAGS) -c llist.c -o $@

clean:
	rm -rf $(BUILD_DIR)
	rm -rf $(DST_DIR)
	rm -f cache_tests/cache_tests
	rm -f cache_tests/cache_tests.exe
	rm -f cache_tests/cache_tests.log

run: $(DST_DIR)/server
	$(DST_DIR)/server

runbg: $(DST_DIR)/server
	nohup stdbuf -oL $(DST_DIR)/server >> $(LOG_FILE) 2>&1 & echo $$! > $(PID_FILE)

stop:
	@if [ -f $(PID_FILE) ]; then \
		kill `cat $(PID_FILE)`; \
		rm -f $(PID_FILE); \
		echo "Server stopped."; \
	else \
		echo "No server is running."; \
	fi


# Quy tắc kiểm tra
TEST_SRC=$(wildcard cache_tests/*_tests.c)
TESTS=$(patsubst %.c,%,$(TEST_SRC))

cache_tests/cache_tests:
	$(CC) cache_tests/cache_tests.c cache.c hashtable.c llist.c -o cache_tests/cache_tests

test:
	tests

tests: clean $(TESTS)
	sh ./cache_tests/runtests.sh

.PHONY: all clean tests

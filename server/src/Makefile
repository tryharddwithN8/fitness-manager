CC=gcc
CFLAGS=-Wall -Wextra -lcjson -lssl -lcrypto -lmysqlclient -lcurl

BUILD_DIR=../build
DST_DIR=../dst
LOG_FILE=../sys.log
PID_FILE=../server.pid

OBJS=$(BUILD_DIR)/server.o $(BUILD_DIR)/net.o $(BUILD_DIR)/file.o $(BUILD_DIR)/mime.o $(BUILD_DIR)/cache.o $(BUILD_DIR)/hashtable.o $(BUILD_DIR)/llist.o $(BUILD_DIR)/sql.o $(BUILD_DIR)/smtp.o

# Đảm bảo thư mục build và dst tồn tại trước khi biên dịch
all: $(BUILD_DIR) $(DST_DIR) $(DST_DIR)/server

$(DST_DIR)/server: $(OBJS)
	$(CC) -o $@ $^ $(CFLAGS)

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)
$(DST_DIR):
	mkdir -p $(DST_DIR)

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

$(BUILD_DIR)/sql.o: sql.c ../include/sql.h
	$(CC) $(CFLAGS) -c sql.c -o $@

$(BUILD_DIR)/smtp.o: smtp.c ../include/smtp.h
	$(CC) $(CFLAGS) -c smtp.c -o $@

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


TEST_SRC=$(wildcard cache_tests/*_tests.c)
TESTS=$(patsubst %.c,%,$(TEST_SRC))

cache_tests/cache_tests:
	$(CC) cache_tests/cache_tests.c cache.c hashtable.c llist.c -o cache_tests/cache_tests

test:
	tests

tests: clean $(TESTS)
	sh ./cache_tests/runtests.sh

.PHONY: all clean tests

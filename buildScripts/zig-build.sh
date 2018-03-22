cd /deps/llvm-6.0.0.src/build
cmake .. -DCMAKE_INSTALL_PREFIX=/deps/local -DCMAKE_PREFIX_PATH=/deps/local -DCMAKE_BUILD_TYPE=Release
make -j4 install

cd /deps/cfe-6.0.0.src/build
cmake .. -DCMAKE_INSTALL_PREFIX=/deps/local -DCMAKE_PREFIX_PATH=/deps/local -DCMAKE_BUILD_TYPE=Release
make -j4 install

export ZIG_BRANCH=master
cd /deps

export CACHE_DATE=2018-03-04
git clone --branch $ZIG_BRANCH --depth 1 https://github.com/zig-lang/zig
mkdir -p /deps/zig/build
cd /deps/zig/build
# Install to /usr and mirror this on the copy
cmake .. \
    -DZIG_LIBC_LIB_DIR=$(dirname $(cc -print-file-name=crt1.o))            \
    -DZIG_LIBC_INCLUDE_DIR=$(echo -n | cc -E -x c - -v 2>&1 |              \
                             grep -B1 "End of search list." |              \
                             head -n1 | cut -c 2- | sed "s/ .*//")         \
    -DZIG_LIBC_STATIC_LIB_DIR=$(dirname $(cc -print-file-name=crtbegin.o)) \
    -DCMAKE_BUILD_TYPE=Release                                             \
    -DCMAKE_PREFIX_PATH=/deps/local                                        \
    -DCMAKE_INSTALL_PREFIX=/usr
make install

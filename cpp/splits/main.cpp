#include <iostream>
#include <vector>

using namespace std;

/* kotlin
var currentOffset = 0
var nextIndex = indexOf(delimiter, currentOffset, ignoreCase)
if (nextIndex == -1 || limit == 1) {
    return listOf(this.toString())
}
val isLimited = limit > 0
val result = ArrayList<String>(if (isLimited) limit.coerceAtMost(10) else 10)
do {
    result.add(substring(currentOffset, nextIndex))
    currentOffset = nextIndex + delimiter.length
    // Do not search for next occurrence if we're reaching limit
    if (isLimited && result.size == limit - 1) break
    nextIndex = indexOf(delimiter, currentOffset, ignoreCase)
} while (nextIndex != -1)
result.add(substring(currentOffset, length))
return result
 */
void split(const string source, const string &delimiter, vector<string> &ret) {
    size_t sourceLength = source.length();
    size_t currentOffset = 0;
    size_t nextIndex = source.find(delimiter);
    //source.npos == LONG_MAX-1
    if (nextIndex == source.npos)return;
    do {
        ret.push_back(source.substr(currentOffset, nextIndex - currentOffset + 1));
        currentOffset = nextIndex + delimiter.length();
        nextIndex = source.find(delimiter, currentOffset);
    } while (nextIndex != source.npos);
    ret.push_back(source.substr(currentOffset, sourceLength - currentOffset + 1));
}

int main() {
    string a = "asdasf,asssAAAf,asssdf";
    vector<string> strs;
    split(a, "f,", strs);
    for (const string &each:strs) {
        cout << each << endl;
    }
    return 0;
}

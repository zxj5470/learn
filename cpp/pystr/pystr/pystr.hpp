#ifndef INCLUDED_PYSTR_H
#define INCLUDED_PYSTR_H
#include <iostream>
#include "../pystring/pystring.h"

using std::string;
using std::ostream;
using std::cout;
using std::endl;
using std::cin;

namespace pystr {

    class PyStr {
    public:
        explicit PyStr(const char *str) : str(string(str)) {}

        inline PyStr(const PyStr &s) = default;

        inline explicit PyStr(const string &str) : str(str) {}


        inline std::string toString() const { return str; }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string with only its first character capitalized.
        ///
        inline PyStr capitalize() const { return PyStr(pystring::capitalize(str)); }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return centered in a string of length width. Padding is done using spaces.
        ///
        inline PyStr center(int width) const { return PyStr(pystring::center(str, width)); }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return the number of occurrences of substring sub in string S[start:end]. Optional
        /// arguments start and end are interpreted as in slice notation.
        ///
        inline int count(const std::string &substr, int start = 0, int end = MAX_32BIT_INT) const {
            return pystring::count(str, substr, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return True if the string ends with the specified suffix, otherwise return False. With
        /// optional start, test beginning at that position. With optional end, stop comparing at that position.
        ///
        inline bool endswith(const std::string &suffix, int start = 0, int end = MAX_32BIT_INT) const {
            return pystring::endswith(str, suffix, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string where all tab characters are expanded using spaces. If tabsize
        /// is not given, a tab size of 8 characters is assumed.
        ///
        inline PyStr expandtabs(int tabsize = 8) const { return PyStr(pystring::expandtabs(str, tabsize)); }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return the lowest index in the string where substring sub is found, such that sub is
        /// contained in the range [start, end). Optional arguments start and end are interpreted as
        /// in slice notation. Return -1 if sub is not found.
        ///
        inline int find(const std::string &sub, int start = 0, int end = MAX_32BIT_INT) const {
            return pystring::find(str, sub, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Synonym of find right now. Python version throws exceptions. This one currently doesn't
        ///
        inline int index(const std::string &sub, int start = 0, int end = MAX_32BIT_INT) const {
            return pystring::index(str, sub, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if all characters in the string are alphanumeric and there is at least one
        /// character, false otherwise.
        ///
        inline bool isalnum() const { return pystring::isalnum(str); }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if all characters in the string are alphabetic and there is at least one
        /// character, false otherwise
        ///
        bool isalpha() {
            return pystring::isalpha(str);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if all characters in the string are digits and there is at least one
        /// character, false otherwise.
        ///
        bool isdigit() {
            return pystring::isdigit(str);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if all cased characters in the string are lowercase and there is at least one
        /// cased character, false otherwise.
        ///
        bool islower() {
            return pystring::islower(str);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if there are only whitespace characters in the string and there is at least
        /// one character, false otherwise.
        ///
        bool isspace() {
            return pystring::isspace(str);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if the string is a titlecased string and there is at least one character,
        /// i.e. uppercase characters may only follow uncased characters and lowercase characters only
        /// cased ones. Return false otherwise.
        ///
        bool istitle() {
            return pystring::istitle(str);;
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return true if all cased characters in the string are uppercase and there is at least one
        /// cased character, false otherwise.
        ///
        bool isupper() {
            return pystring::isupper(str);;
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a string which is the concatenation of the strings in the sequence seq.
        /// The separator between elements is the str argument
        ///
        PyStr join(const std::vector<std::string> &seq) {
            return PyStr(pystring::join(str, seq));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return the string left justified in a string of length width. Padding is done using
        /// spaces. The original string is returned if width is less than str.size().
        ///
        PyStr ljust(int width) {
            return PyStr(pystring::ljust(str, width));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string converted to lowercase.
        ///
        PyStr lower() {
            return PyStr(pystring::lower(str));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string with leading characters removed. If chars is omitted or None,
        /// whitespace characters are removed. If given and not "", chars must be a string; the
        /// characters in the string will be stripped from the beginning of the string this method
        /// is called on (argument "str" ).
        ///
        PyStr lstrip(const std::string &chars = "") {
            return PyStr(pystring::lstrip(str, chars));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string, concatenated N times, together.
        /// Corresponds to the __mul__ operator.
        /// 
        PyStr mul(int n) {
            return PyStr(pystring::mul(str, n));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Split the string around first occurance of sep.
        /// Three strings will always placed into result. If sep is found, the strings will
        /// be the text before sep, sep itself, and the remaining text. If sep is
        /// not found, the original string will be returned with two empty strings.
        ///
        void partition(const std::string &sep, std::vector<std::string> &result) {
            pystring::partition(str, sep, result);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string with all occurrences of substring old replaced by new. If
        /// the optional argument count is given, only the first count occurrences are replaced.
        ///
        inline PyStr replace(const std::string &oldstr, const std::string &newstr, int count = -1) const {
            return PyStr(pystring::replace(str, oldstr, newstr, count));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return the highest index in the string where substring sub is found, such that sub is
        /// contained within s[start,end]. Optional arguments start and end are interpreted as in
        /// slice notation. Return -1 on failure.
        ///
        inline int rfind(const std::string &sub, int start = 0, int end = MAX_32BIT_INT) {
            return pystring::rfind(str, sub, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Currently a synonym of rfind. The python version raises exceptions. This one currently
        /// does not
        ///
        inline int rindex(const std::string &sub, int start = 0, int end = MAX_32BIT_INT) {
            return pystring::rindex(str, sub, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return the string right justified in a string of length width. Padding is done using
        /// spaces. The original string is returned if width is less than str.size().
        ///
        inline PyStr rjust(int width) {
            return PyStr(pystring::rjust(str, width));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Split the string around last occurance of sep.
        /// Three strings will always placed into result. If sep is found, the strings will
        /// be the text before sep, sep itself, and the remaining text. If sep is
        /// not found, the original string will be returned with two empty strings.
        ///
        inline void rpartition(const std::string &sep, std::vector<std::string> &result) {
            pystring::rpartition(str, sep, result);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string with trailing characters removed. If chars is "", whitespace
        /// characters are removed. If not "", the characters in the string will be stripped from the
        /// end of the string this method is called on.
        ///
        inline PyStr rstrip(const std::string &chars = "") {
            return PyStr(pystring::rstrip(str, chars));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Fills the "result" list with the words in the string, using sep as the delimiter string.
        /// If maxsplit is > -1, at most maxsplit splits are done. If sep is "",
        /// any whitespace string is a separator.
        ///
        inline void split(std::vector<std::string> &result, const std::string &sep = "", int maxsplit = -1) {
            pystring::split(str, result, sep, maxsplit);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Fills the "result" list with the words in the string, using sep as the delimiter string.
        /// Does a number of splits starting at the end of the string, the result still has the
        /// split strings in their original order.
        /// If maxsplit is > -1, at most maxsplit splits are done. If sep is "",
        /// any whitespace string is a separator.
        ///
        inline void rsplit(std::vector<std::string> &result, const std::string &sep = "",
                           int maxsplit = -1) {
            pystring::rsplit(str, result, sep, maxsplit);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a list of the lines in the string, breaking at line boundaries. Line breaks
        /// are not included in the resulting list unless keepends is given and true.
        ///
        inline void splitlines(std::vector<std::string> &result, bool keepends = false) {
            pystring::splitlines(str, result, keepends);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return True if string starts with the prefix, otherwise return False. With optional start,
        /// test string beginning at that position. With optional end, stop comparing string at that
        /// position
        ///
        inline bool startswith(const std::string &prefix, int start = 0, int end = MAX_32BIT_INT) {
            return pystring::startswith(str, prefix, start, end);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string with leading and trailing characters removed. If chars is "",
        /// whitespace characters are removed. If given not "",  the characters in the string will be
        /// stripped from the both ends of the string this method is called on.
        ///
        inline PyStr strip(const std::string &chars = "") {
            return PyStr(pystring::strip(str, chars));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string with uppercase characters converted to lowercase and vice versa.
        ///
        inline PyStr swapcase() {
            return PyStr(pystring::swapcase(str));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a titlecased version of the string: words start with uppercase characters,
        /// all remaining cased characters are lowercase.
        ///
        inline PyStr title() {
            return PyStr(pystring::title(str));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string where all characters occurring in the optional argument
        /// deletechars are removed, and the remaining characters have been mapped through the given
        /// translation table, which must be a string of length 256.
        ///
        inline PyStr translate(const std::string &table, const std::string &deletechars = "") {
            return PyStr(pystring::translate(str, table, deletechars));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return a copy of the string converted to uppercase.
        ///
        inline PyStr upper() {
            return PyStr(pystring::upper(str));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief Return the numeric string left filled with zeros in a string of length width. The original
        /// string is returned if width is less than str.size().
        ///
        inline PyStr zfill(int width) {
            return PyStr(pystring::zfill(str, width));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////
        /// @brief function matching python's slice functionality.
        ///
        inline PyStr slice(int start = 0, int end = MAX_32BIT_INT) {
            return PyStr(pystring::slice(str, start, end));
        }


        inline friend ostream &operator<<(std::ostream &lhs, const PyStr &rhs) {
            lhs << (rhs).toString().c_str();
            return lhs;
        }

    private:
        std::string str;
    };

};

#endif
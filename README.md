# HashTableLinearP

## This program is part of COT 5405 Design & Analysis Of Algorithms course. 

### Algorithms Design Project to order records using a list.

**Step 1.**  Write a c++ program to create a hash table (containing 1000 slots to store records) store the records using account_num as the key field. 

**Step 2.**  Use the method called linear probing to handle collisions.
        
### Sample data
<pre>
2342 Kelly Mathis 03 12 1989 12345.90 A 895-059-0950
2781 GoodWin Jim  03 14 1987 28292.90 c 894-903-9383
1931 Terry Jones  01 20 2019 34903.90 v 939-903-9303
</pre>
 

### Sample report
<pre>
                                  Employee Report                      2021/04/15 14:46:46
                                     *************
     Acct#            Last           First         Date of          Annual      Department             Age           Phone
                      Name         Initial           Birth          Salary            Code                          Number

     0003*            Earl              C.     Set.17,2000      $39,393.90               W              21         850-340-9090
     1003*           James              W.     Set.23,2001      $34,344.90               R              20         850-534-9033
     1103             MarK              J.     Set.19,2000      $29,292.90               A              21         850-345-9030
     1192              Joe              J.     Set.18,2000      $19,192.90               X              21         850-123-0303
     1534*           James              E.     Set.22,2001      $50,000.03               T              20         850-345-8939
     1785             Mary              S.     Set.26,2001      $83,838.89               S              20         850-529-9039
* Indicates collisions
</pre>

@echo off
set SQL_DIR=c:\0_workspace\KP_Seam01\sql\
pushd %cd%
cd "c:\Program Files\MySQL\MySQL Server 5.7\bin"

set SQL_SCRIPT=%SQL_DIR%\dropDB.txt
mysql --user=root --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%"
:: pause

set SQL_SCRIPT=%SQL_DIR%\createDB.txt
mysql --user=root --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%"
:: pause

set SQL_SCRIPT=%SQL_DIR%\initDB.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_seam01
:: pause

set SQL_SCRIPT=%SQL_DIR%\populateDB.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_seam01

set SQL_SCRIPT=%SQL_DIR%\initDB_forJBoss.txt
mysql --user=kp --password=mikimiki --verbose --execute="\. %SQL_SCRIPT%" kp_seam01
:: pause
popd
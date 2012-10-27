#!/bin/bash

LOG_FILE=/var/backups/autograder/logs/database/hourly.log
STORAGE_DIR=/var/backups/autograder/archives/database/hourly/

# MySQL 
DB_USER="root"
DB_PASS="c0ff33"
DB_NAME="autograder"

# Current Hour
HOUR=`date +%H`

# Where to place backups
BAK_DIR="${STORAGE_DIR}backup_${HOUR}"
BAK_FILE="${STORAGE_DIR}backup_${HOUR}/backup.gz"
LATEST_FILE="/var/backups/latest-backup/backup.gz"

# Does directory exist. If not, create it.
if [ ! -d "$BAK_DIR" ]; then
	mkdir -m 777 $BAK_DIR	
fi

# Send an email notification is a backup fails
notify () {
	echo "$1" | mailx -s "Unsuccessful Hourly Backup" ag-l@ucsd.edu
}

# Log function - records stderr or success to log file
log () {

	read error

	# If not empty, then there was an error
	if [ ! -z "$error" ]; then
		echo "$(timestamp) $error" >> $LOG_FILE;
		notify "$error"
	else
		echo "$(timestamp) Successful Backup" >> $LOG_FILE
		echo " |--> Placed in: $BAK_DIR" >> $LOG_FILE
	fi 
}

# Simple timestamp function
timestamp () {

	echo "$(date +'[%F %H:%M:%S]')"
}

mysqldump --opt --routines --user $DB_USER --password=$DB_PASS $DB_NAME 2> >(log) | gzip > $BAK_FILE

#place in latest-backup
cp $BAK_FILE $LATEST_FILE
chown backup:backup $LATEST_FILE

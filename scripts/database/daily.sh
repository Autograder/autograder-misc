#!/bin/bash

LOG_FILE=/var/backups/autograder/logs/database/daily.log

destination='/var/backups/autograder/archives/database/daily/backup_'
source='/var/backups/autograder/archives/database/hourly/backup_23'

# Send an email notification is a backup fails
notify () {
        echo "$1" | mailx -s "Unsuccessful Daily Backup" ag-l@ucsd.edu
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
                echo " |--> Placed in: ${destination}1" >> $LOG_FILE
        fi
}

# Simple timestamp function
timestamp () {

        echo "$(date +'[%F %H:%M:%S]')"
}

# If 3-days ago directory doesn't exist, create it
if [ ! -d "${destination}"'3' ];  then
	mkdir -m 777 -p $destination'3'
fi

# If 2-days ago directory exists, copy contents to 3 days ago
if [ -f "${destination}"'2/backup.gz' ]; then
	cp -r $destination'2/backup.gz' $destination'3'
else
	mkdir -p $destination'2'	#if it didn't already exist, create it
fi

# Same for last day's copy to 2 days ago
if [ -f "${destination}"'1/backup.gz' ]; then
	cp -r $destination'1/backup.gz' $destination'2'
else
	mkdir -p $destination'1'
fi

# Copy last hourly backup to this day's backup
cp -r $source'/backup.gz' $destination'1' 2> >(log)


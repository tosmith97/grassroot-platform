#!/bin/bash

if [[ -f /var/grassroot/.pid ]]; then
        kill -s SIGINT `cat /var/grassroot/.pid`;
        rm -f  /var/grassroot/.pid;
fi

echo STOPPING DONE
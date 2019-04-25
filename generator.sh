#!/bin/bash


function generate(){
 	echo $1
	#local dirPath = $0
	#cp ./template.html "$dirPath""index.html"
	declare -a dirs
	local i=1
	for d in $1;
	do
		generate "$1" "/" "$d";
		dirs[i++]="${d%/}"
	done
	for((i=1;i<=${#dirs[@]};i++))
	do
	    echo $i "${dirs[i]}"
	done
}

generate "medium.com"

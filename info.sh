echo $(uname -o) > $1
echo $(uname -r) >> $1
echo $(grep "model name" /proc/cpuinfo | uniq) >> $1
echo $(whoami) >> $1
echo $(uptime -p) >> $1
echo $(lsmem | tail -2 | head -1) | cut -f4 -d" " >> $1
echo $(echo $(echo $(free -m | head -2 | tail -1) | cut -f4 -d" ")M) >> $1
echo $(echo $(df -h | grep "/$") | cut -f2 -d " ") >> $1

# Competition Login Instructions

Note: Replace `N` in `VVSTeamN` and `teamN` with your team number. Replace `<IP>` with your servers IP address (you may want to do this is updates to DNS are propogating slowly during the competition).

# First time setup

You will want to login as the vvs user, however by default your SSH keys were generated for the ubuntu user. You should copy your authorized SSH key into the vvs's authorized keys. Important, you should replace default ssh key for vvs because it is a known ssh key and other teams will be able to log as your vvs user if you do not change it.

- From a linux or mac machine open the directory and cd to where you downloaded the VVSTeamN.pem file. Alternatively on Windows you can download the Putty program as described at [https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html).

    chmod 400 VVSTeamN.pem
    ssh -i VVSTeamN.pem ubuntu@teamN.vulnerablevideoservice.com
OR
    ssh -i VVSTeamN.pem ubuntu@<ip>

- Change to super user and copy the ssh keys then exit super user. 

    sudo su
    cat /home/ubuntu/.ssh/authorized_keys > /home/vvs/.ssh/authorized_keys
    exit

- Log out of the ubuntu user.

    exit

- You can now log in to vvs account from SSH.

    ssh -i VVSTeamN.pem vvs@teamN.vulnerablevideoservice.com
OR
    ssh -i VVSTeamN.pem vvs@<ip>

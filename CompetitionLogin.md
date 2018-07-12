# Competition Login Instructions

Note: Replace `N` in `VVSTeamN` and `teamN` with your team number. Replace `<IP>` with your servers IP address (you may want to do this is updates to DNS are propogating slowly during the competition).

# Default Passwords
There are two accounts on the competition server. However the server is configured to only accept SSH connections that send an SSH certificate (passwords are not accepted), so these passwords are for local access commands only (such as running `sudo`).

- `ubuntu`:`WilEXamixt`
- `vvs`:`naTardPewd`

You can change the password for a user you are logged in as by typing `passwd`. You can change the password of another user by typing `sudo passwd <user>` (replacing `<user>` with the name of the user account you wish to change the password for).

# First Time Setup

You will want to login as the vvs user, however by default your SSH keys were generated for the ubuntu user. You should copy your authorized SSH key into the vvs's authorized keys. Important, you should replace default ssh key for vvs because it is a known ssh key and other teams will be able to log as your vvs user if you do not change it.

From a linux or mac machine open the directory and cd to where you downloaded the VVSTeamN.pem file. Alternatively on Windows you can download the Putty program as described at [https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html).

    chmod 400 VVSTeamN.pem
    ssh -i VVSTeamN.pem ubuntu@teamN.vulnerablevideoservice.com
    
OR

    ssh -i VVSTeamN.pem ubuntu@<ip>

Change to super user and copy the ssh keys then exit super user. 

    sudo su
    cat /home/ubuntu/.ssh/authorized_keys > /home/vvs/.ssh/authorized_keys
    exit

Log out of the ubuntu user.

    exit

You can now log in to vvs account from SSH.

    ssh -i VVSTeamN.pem vvs@teamN.vulnerablevideoservice.com
    
OR

    ssh -i VVSTeamN.pem vvs@<ip>
    
After your first login the website will not be running yet. So you should restart the website (see next section).

## Restarting Website

If your website is not running you can try running the restart script in the website deployment folder. Log in as the vvs user and then run the commands below.

    cd /home/vvs/deployment/scripts
    sudo ./restart.sh

You should now be able to go to [http://teamN.vulnerablevideoservice.com](http://teamN.vulnerablevideoservice.com).

## Pushing New Code to Website

Inside the PACSeminar2018 VM open Eclipse and edit the code to fix vulnerabilities. After testing the code locally to make sure it works you can deploy it to the web server.

1) Right click on the project file `create_deployment.ant.xml` and navigate to `Run As` &gt; `Ant Build`.
2) Right click on the project file `deploy.sh` and ensure that the variable `DEPLOYMENT_SERVER` is set to your team domain name (example: `teamN.vulnerablevideoservice.com`) or the team IP address.
3) In the `deploy.sh` script also ensure that the variable `CERTIFICATE` is set to the path to your team SSH private key (example: `/home/pac/Desktop/VVSTeamN.pem`). Remember that the private key permissions need to be set correctly (`chmod 400 VVSTeamN.pem`).
4) Finally run the `deploy.sh` script by running `cd /home/pac/workspace/VVS` followed by `./deploy.sh`.

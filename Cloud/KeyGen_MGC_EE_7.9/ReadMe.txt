This is license generator for almost all Mentor Graphics products.
It was tested on PADS 2007.3 & HDL Designer 2007.1b with patched mgls.dll.

NOTE: You should replace all instances of MGLS.DLL 1564672 bytes size with
provided on in PADS2007.3 installation directory to use license. This is
very important cause new MGLS.DLL utilizes CRO long signs and the only
way is to use patched verification routine in MGLS.DLL.

To generate license edit corresponding .src license file:
mdltech_fl.src - for counted floating license, update HostName and HostID 
		 fields in SERVER line to match your host parameters. 
		 Besides, your should correct DAEMON line to match rigth
		 path to you mgcld.exe location.
		 Note: HOSTID=ANY doesn't work.
mdltech_nl.src - for uncounted nodelocked license, update HOSTID field
		 in FEATURE line to match your host HOSTID.

You can use lmutil to obtain you HOSTID:
Run "lmutil.exe hostid -n -ether" and take first value in produced string.

Run corresponding .bat-file to generate license:
make_float.bat 		- floating license generation.
make_nodelocked.bat	- nodelocked license generation.

After all, you'll be given right license.dat. :)
Copy it to proper location and modify LM_LICENSE_FILE environment variable
to point the location of generated and copied license.dat.

PS: You are free to add FEATURES to .src files, but your should preserve
original syntax. Moreover, you can modify ISSUER field. 


#! /usr/bin/env python
# Run this to generate all the initial makefiles, etc.

import os, re, sys

def run_tool(tool, args = ''):
    print '+ running %s%s ...' % (tool, args)
    if os.system(tool + ' ' + args):
        print tool, 'failed'
        sys.exit(1)

def version_check(command, url, major, minor, micro = 0):
    print '  checking for %s >= %d.%d.%d ...' % \
          (command, major, minor, micro),

    vpipe = os.popen(command + ' --version')
    vline = vpipe.readline()
    vpipe.close()
    vmatch = re.search(r'\s(?P<number>[\d.]+)\s', vline)
    if not vmatch or not vmatch.groups():
        print 'not found.'

    version = vmatch.group('number').split('.')
    pmajor = int(version[0])
    pminor = int(version[1])
    pmicro = len(version) > 2 and version[2] or '0'
    pmicro = int(pmicro)

    version_ok = 1
    if pmajor < major or \
       pminor < minor or \
       pmicro < micro: version_ok = 0

    if version_ok:
        print 'found %d.%d.%d, ok.' % (pmajor, pminor, pmicro)
        return 0
    else:
        print 'found %d.%d.%d, not ok !' % (pmajor, pminor, pmicro)
        print 'Please download the appropriate package for your distribution,'
        print 'or get the source tarball at <%s>.' % url
        return 1

die = 0
print '+ checking for build tools ...'
die += version_check('autoconf', 'ftp://ftp.gnu.org/pub/gnu/autoconf/', 2, 13)
die += version_check('automake', 'ftp://ftp.gnu.org/pub/gnu/automake/', 1, 7)

if die > 0:
    print
    print 'It looks like your system needs the proper versions of some GNU'
    print 'build tools. Please get them and try again.'
    sys.exit(1)

if not os.path.isfile('ccmtools-autogen-top-check'):
    print 'You must run this script in the top level ccmtools source directory.'
    sys.exit(1)

run_tool('aclocal')
run_tool('autoconf')
run_tool('automake', ' -a -c')
run_tool('./configure', ' ' + ' '.join(sys.argv[1:]))

print 'Package configured. Now type "make" to compile and "make install" to'
print 'install. Please note the installation warnings about setting your'
print 'CLASSPATH correctly.'


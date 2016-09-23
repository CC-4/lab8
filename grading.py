import os
import gzip
import cPickle

def mkdir():
    os.system('rm -rf ' + os.path.join('grading'))
    os.system('mkdir ' + os.path.join('grading'))
    os.system('rm -rf ' + os.path.join('grading', 'test-output'))
    os.system('mkdir ' + os.path.join('grading', 'test-output'))

def main():
    f = gzip.open(os.path.join('autograder', 'data.pkl.gz'), 'rb')
    files, ans = cPickle.load(f)
    f.close()
    mkdir()
    print('##################### Autograder #######################')
    count = len(ans)
    assert(files.keys() == ans.keys())
    for file in ans.keys():
        d = files[file]
        f = os.path.join('grading', file)
        fw = open(f, 'w')
        fw.write(d)
        fw.close()
        fans = open(os.path.join('grading', file+'.output'), 'w')
        fans.write(ans[file])
        fans.close()
        os.system('rm -rf grading/tmp')
        os.system('./semant ' + f + ' >>grading/tmp 2>&1')
        d = open('grading/tmp', 'r').read()
        if(d != ans[file]):
            print('+0 '.rjust(10) + '(' + file +')')
            fans = open(os.path.join('grading', 'test-output', file+'.output'), 'w')
            fans.write(d)
            fans.close()
            cmd = 'diff grading/tmp ' + os.path.join('grading', file+'.output')
            cmd = cmd + ' >' + os.path.join('grading', 'test-output', file+'.diff')
            os.system(cmd)
            count = count - 1
        else:
            print('+1 '.rjust(10) + '(' + file +')')

    os.system('rm -rf grading/tmp')
    print('')
    if(count == len(ans)):
        print('-> All ok')
    st = '+------------------------------------------------------+'
    print(st)
    print('| ' + ('Score: ' + str(count) + '/' + str(len(ans))).rjust(len(st)-3)+'|')
    print('+------------------------------------------------------+')

if __name__ == '__main__':
    main()

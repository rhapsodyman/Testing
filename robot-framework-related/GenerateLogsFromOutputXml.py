import datetime
import sys
from robot.api import ExecutionResult, ResultVisitor


class GenerateLogsFromOutputXml:

    def __init__(self, inpath, outpath):
        self.inpath = inpath
        self.outpath = outpath

    def generate_text_logs(self):
        result = ExecutionResult(self.inpath)
        result.visit(MyVisitor(self.outpath))


class MyVisitor(ResultVisitor):

    def __init__(self, outpath):
        self.outpath = outpath
        self.logfile = open(outpath, 'w')
        self.current_test = ''

    def _write(self, message):
        # now = datetime.datetime.now()
        # self.logfile.write(message + "\n")
        self.logfile.write("[%s]   %s" % (self.current_test, message) + '\n')
        # self.logfile.flush()

    def start_test(self, test):
        self.current_test = test.name
        self._write("Starting Test: %s " % test.name)

    def start_keyword(self, keyword):
        self.current_test = keyword.parent.name
        self._write("Keyword: \"%s\" with status \"%s\" with arguments %s" % (keyword.kwname, keyword.status, keyword.args))

    def start_message(self, msg):
        self._write("Message:  %s %s" % (msg.level, msg.message))


if __name__ == '__main__':
    # gen = GenerateLogsFromOutputXml(sys.argv[1], sys.argv[2])

    # gen = GenerateLogsFromOutputXml('output.xml', 'generated_logs.txt')
    gen = GenerateLogsFromOutputXml('C:/Users/Andrei/AppData/Local/Temp/RIDE6epcqg.d/output.xml', 'generated_logs.txt')

    # C:/Users/Andrei/AppData/Local/Temp/RIDE6epcqg.d/report.html

    gen.generate_text_logs()

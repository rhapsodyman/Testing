import os
import re

from ExcelReader import ExcelReader
from distutils.dir_util import copy_tree


class GenerateIterations:

    def __init__(self, in_folder, out_folder):
        self.in_folder = in_folder
        self.out_folder = out_folder

    def generate_for_dir(self):
        copy_tree(self.in_folder, self.out_folder)

        # traverse root directory, and list directories as dirs and files as files
        for root, dirs, files in os.walk(self.out_folder):
            for f in files:
                if f.endswith('.robot'):
                    should_remove = self.generate_for_file(root, f)

                    if should_remove:
                        os.remove(os.path.join(root, f))

    def generate_for_file(self, dir_path, file_name):

        name = os.path.splitext(file_name)[0]

        # read file and see if it has TestDataPath variable
        with open(os.path.join(dir_path, file_name), 'r') as test_file:
            content = test_file.read()
            match_obj = re.search(r'\$\{TestDataPath\}[ \t]+(.+?)\n', content, re.MULTILINE)

            if match_obj:
                # print match_obj.group(1)

                reader = ExcelReader(match_obj.group(1))
                rows = reader.read_workbook()

                if len(rows) > 2:
                    # will generate multiple files - generate file and inject the row number in it
                    for iterat in range(0, len(rows)):
                        new_name = '{0}[{1}].robot'.format(name, iterat + 1)
                        with open(os.path.join(dir_path, new_name), 'w') as out_file:
                            new_content = content.replace('%RowNum%', str(iterat))
                            out_file.write(new_content)
                    return True

        return False


if __name__ == '__main__':
    gen = GenerateIterations('tests', 'generated')
    # gen.generate_for_file('Test.robot')

    gen.generate_for_dir()

from os import system, chdir, environ
from glob import glob
from shutil import copyfile

gamedir = environ["TWO_GAME_DIR"]
jarpath = gamedir + "\\bin\\game.jar"
modsdir = gamedir + "\\mods\\"

chdir("build")

targets = glob(r"modding\*.class")
cmd = f"7z u {jarpath} {' '.join(targets)}"
system(cmd)

targets = glob("*.zip")
for t in targets:
    outfile = f"{modsdir}\\{t}"
    copyfile(t, outfile)
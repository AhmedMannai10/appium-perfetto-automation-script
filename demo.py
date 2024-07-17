import subprocess
import time
import os
import signal
import sys

def run_command(command, shell=True):
    try:
        process = subprocess.Popen(command, shell=shell, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        return process
    except FileNotFoundError:
        print(f"Error: The command '{command[0]}' was not found. Please ensure it's installed and in your PATH.")
        sys.exit(1)
    except PermissionError:
        print(f"Error: You don't have permission to execute '{command[0]}'. Try running the script as administrator.")
        sys.exit(1)
    except Exception as e:
        print(f"An unexpected error occurred: {e}")
        sys.exit(1)
   
def main():
    # Start Perfetto tracing with the specified parameters
    perfetto_cmd = ["Python",
        ".\\record_android_trace",
        "-o", "trace_file.perfetto-trace",
        "-t", "40s",
        "-b", "64mb",
        "sched", "freq", "idle", "am", "wm", "gfx", "view", "binder_driver", "hal", "dalvik", "camera", "input", "res", "memory"
    ]
    perfetto_process = run_command(perfetto_cmd)
    
    print("Started Perfetto tracing...")
    
    # Give Perfetto a moment to start
    time.sleep(2)

    # project_path = r"C:\Users\ahm\MobilePerf\Demo\Java-Appium-Automation" 
    project_path = ".\\Java-Appium-Automation"
    
    # Run the Java test
    java_cmd = ["mvn", "test", "-f" , ".\\Java-Appium-Automation", "-Dtest=TC1"]
    java_process = run_command(java_cmd)
    
    print("Started Java test...")
    
    # Wait for the Java test to complete
    java_process.wait()
    
    print("Java test completed.")
    
    # Stop Perfetto tracing (it should stop automatically after 30s, but we'll make sure)
    if perfetto_process.poll() is None:
        os.kill(perfetto_process.pid, signal.SIGINT)
        perfetto_process.wait()
    
    print("Stopped Perfetto tracing.")
    
    # Print output from Java test
    stdout, stderr = java_process.communicate()
    print("Java test output:")
    print(stdout.decode())
    print(stderr.decode())

if __name__ == "__main__":
    main()
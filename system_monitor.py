import psutil
import time
import curses

def get_size(bytes, suffix="B"):
    """
    Scale bytes to its proper format
    e.g:
        1253656 => '1.20MB'
        1253656678 => '1.17GB'
    """
    factor = 1024
    for unit in ["", "K", "M", "G", "T", "P"]:
        if bytes < factor:
            return f"{bytes:.2f}{unit}{suffix}"
        bytes /= factor

def monitor_system_resources(stdscr):
    stdscr.nodelay(True)  # Make getch non-blocking
    stdscr.clear()
    stdscr.refresh()

    while True:
        # Fetch CPU usage
        cpu_percent = psutil.cpu_percent(interval=0.5)
        
        # Fetch memory usage
        memory_info = psutil.virtual_memory()
        total_memory = get_size(memory_info.total)
        used_memory = get_size(memory_info.used)
        memory_percent = memory_info.percent
        
        # Fetch disk usage
        disk_usage = psutil.disk_usage('/')
        total_disk = get_size(disk_usage.total)
        used_disk = get_size(disk_usage.used)
        disk_percent = disk_usage.percent
        
        # Clear the screen
        stdscr.clear()
        
        # Display the results
        stdscr.addstr(0, 0, f"CPU Usage: {cpu_percent}%")
        stdscr.addstr(1, 0, f"Memory Usage: {used_memory}/{total_memory} ({memory_percent}%)")
        stdscr.addstr(2, 0, f"Disk Usage: {used_disk}/{total_disk} ({disk_percent}%)")
        stdscr.addstr(3, 0, "-" * 40)
        
        # Refresh the screen
        stdscr.refresh()
        
        # Check for key press to exit
        if stdscr.getch() == ord('q'):
            break
        
        # Wait for 0.1 seconds before the next update
        time.sleep(0.5)

if __name__ == "__main__":
    print("System Resource Monitor Tool")
    print("Press 'q' to exit\n")
    try:
        curses.wrapper(monitor_system_resources)
    except KeyboardInterrupt:
        print("\nMonitoring stopped.")

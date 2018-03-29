using System;
using DevZH.UI;
using DevZH.UI.Drawing;
/**
 * Yeah , I exactly like coding with Java Style even though in a C# file.
 */
namespace bnd.ZxGUI{
    /// <summary>
    /// Yeah, I exactly don't like this kind of doc-comments...
    /// wth for function extensions in CSharp???`public static` and `this`
    /// </summary>
    public static class MouseUtil{
        public static bool IsUpEvent(this AreaMouseEvent e) {
            return e.Up == 1;
        }
        public static bool IsDownEvent(this AreaMouseEvent e) {
            return e.Down == 1;
        }
    }
    
    public static class Program{
        public static void Main(string[] args) {
            var app = new Application(false);
            InitMenus(app);
            var window = new MainWindow("啊♂乖乖站好", 320, 240, true) {
                AllowMargins = true
            };
            app.Run(window);
        }

        public class AHandler : IAreaHandler{
            public void Draw(AreaBase area, ref AreaDrawParams param) {
            }

            public void MouseEvent(AreaBase area, ref AreaMouseEvent mouseEvent) {
                Console.WriteLine(mouseEvent.IsDownEvent() + "," + mouseEvent.IsUpEvent()+"("+mouseEvent.X+","+mouseEvent.Y+")");
            }

            public void MouseCrossed(AreaBase area, bool left) {
            }

            public void DragBroken(AreaBase area) {
            }

            public bool KeyEvent(AreaBase area, ref AreaKeyEvent keyEvent) {
                Console.WriteLine(keyEvent.ToString());
                return true;
            }
        }

        private static void InitMenus(Application app) {
            var file = new Menu("File");
            file.Add("New");
            file.Add("Open", MenuItemTypes.Common, obj => { Console.WriteLine(obj + ",23"); });
            file.AddSeparator();
            app.OnShouldExit += (sender, args) => {
                Console.WriteLine("23333");
                MessageBox.Show("23333");
                app.Exit();
            };
            var edit = new Menu("Edit");
            var undo = edit.Add("Undo");
            undo.Enabled = false;
            edit.AddSeparator();
        }
    }
}

import shared
import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
			AmdImagePickerView()
//                .ignoresSafeArea(.all)
		}
	}
}

struct AmdImagePickerView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        AmdImagePickerViewControllerKt.AmdImagePickerViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
}

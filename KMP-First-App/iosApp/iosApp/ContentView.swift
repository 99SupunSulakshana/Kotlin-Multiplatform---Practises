import SwiftUI
import Shared

struct ContentView: View {
    
    @ObservedObject var viewModel: ViewModel
    
    @State private var showContent = false
    var body: some View {
        List(viewModel.users, id: \.self){ user in
            if let name = user.name {
                VStack {
                    Spacer()
                    HStack{
                        AsyncImage(url: URL(string: user.picture?.thumbnail))
                            .frame(width: 50, height: 50)
                            .clipShape(RoundedRectangle(cornerSize: CGSize(width: 10, height: 10)))
                        VStack(alignment: <#T##HorizontalAlignment#>, content: {
                            Text("\(name.firs ?? "Firstname") \(name.last ?? "LastName")")
                            Text(user.phone ?? "Phone")
                            
                        })
                    }
                    Spacer()
                }
            }
        }.onAppear{
            self.viewModel.observeDataFlow()
        }
    }
}

extension ContentView{
    @MainActor
    class ViewModel: ObservableObject {
        var homeRepository: HomeRepository = HomeRepository.init()
        
        @Published var user: [RandomUser] = []
        
        func observeDataFlow(){
            Task {
                for await user in homeRepository.getUsers(){
                    self.users.append(contentsOf: user)
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

using Microsoft.AspNetCore.Mvc;
using TooHumanCalc.Api.Services;

namespace TooHumanCalc.Api.Controllers
{
	[Route("api/trees/classes")]
	public class ClassTreesController : Controller
	{
		[HttpGet]
		public IActionResult GetAllClasses() => Ok(TreesService.ClassTrees);
	}
}
